package org.sjhstudio.flow.bookproject.presentation.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.sjhstudio.flow.bookproject.R
import org.sjhstudio.flow.bookproject.databinding.ActivityMainBinding
import org.sjhstudio.flow.bookproject.domain.model.Book
import org.sjhstudio.flow.bookproject.presentation.base.BaseActivity
import org.sjhstudio.flow.bookproject.presentation.base.UiState
import org.sjhstudio.flow.bookproject.presentation.ui.adapter.BookAdapter
import org.sjhstudio.flow.bookproject.presentation.ui.common.MessageDialog
import org.sjhstudio.flow.bookproject.presentation.ui.view.RecentSearchActivity.Companion.EXTRA_QUERY
import org.sjhstudio.flow.bookproject.presentation.ui.viewmodel.MainViewModel
import org.sjhstudio.flow.bookproject.presentation.util.Constants.SORT_OF_ACCURACY
import org.sjhstudio.flow.bookproject.presentation.util.Constants.SORT_OF_PUBLISH_DATE
import org.sjhstudio.flow.bookproject.presentation.util.showSnackMessage
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var imm: InputMethodManager

    private val viewModel: MainViewModel by viewModels()
    private val bookAdapter by lazy {
        BookAdapter(
            onClickBook = { book ->
                openBrowser(book)
            },
            insertBookMark = { book ->
                viewModel.insertBookmark(book)
                binding.root.showSnackMessage("북마크가 저장되었습니다.")
            },
            deleteBookmark = { book ->
                viewModel.deleteBookmark(book)
                binding.root.showSnackMessage("북마크가 삭제되었습니다.")
            }
        )
    }

    private val bookmarkLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            with(viewModel) {
                lastBookList?.query?.let { query ->
                    searchBook(query, 1, getSearchFilter())
                }
            }
        }

    private val recentSearchLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val query =
                    result.data?.getStringExtra(EXTRA_QUERY) ?: return@registerForActivityResult
                bookAdapter.submitList(null)
                viewModel.searchBook(query, 1, getSearchFilter())
                binding.etSearch.setText(query)
            }
        }

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus

        view?.let { v ->
            if (v is TextInputEditText && ev?.action == MotionEvent.ACTION_DOWN) {
                val x = ev.rawX
                val y = ev.rawY
                val outLocation = IntArray(2)

                v.getLocationOnScreen(outLocation)

                if (outLocation[0] < x || outLocation[0] > x + v.width ||
                    outLocation[1] < y || outLocation[1] > y + v.height
                ) {
                    clearKeyboard(v)
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        with(binding) {
            supportActionBar?.title = getString(R.string.label_search)

            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.text?.also { bookName ->
                        if (bookName.trim().isNotEmpty()) {
                            initBookList()
                            viewModel.searchBook(bookName.toString(), 1, getSearchFilter())
                        }
                    }?.run {
//                        clear() // 검색창 초기화
                        clearKeyboard(etSearch) // 키보드 숨기기
                    }
                }
                false
            }

            chipGroupFilter.setOnCheckedStateChangeListener { _, _ ->
                Log.e(LOG, "change checked chip state")
                initBookList()
                if (!etSearch.text.isNullOrEmpty()) {
                    viewModel.searchBook(etSearch.text.toString(), 1, getSearchFilter())
                }
            }

            rvBook.apply {
                adapter = bookAdapter
                itemAnimator.takeIf { animator ->
                    animator is SimpleItemAnimator
                }?.let { animator ->
                    (animator as SimpleItemAnimator).supportsChangeAnimations = false
                }

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (bookAdapter.itemCount == 0) return  // 어뎁터 내 리스트가 초기화 되는 경우 예외처리

                        val linearLayoutManager = layoutManager as LinearLayoutManager
                        val lastPosition = linearLayoutManager.itemCount - 1
                        val lastVisiblePosition =
                            linearLayoutManager.findLastCompletelyVisibleItemPosition()

                        if (lastPosition == lastVisiblePosition) {
                            viewModel.lastBookList?.let { last ->
                                val end = last.start + last.display - 1
                                if (end < last.total) {
                                    viewModel.searchBook(last.query, end + 1, getSearchFilter())
                                }
                            }
                        }
                    }
                })
            }
        }
    }

    private fun observeData() {
        with(viewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    bookmarkList.collectLatest { list ->
                        // collect bookmark DB
                        Log.e(LOG, "bookmark list : ${list.map { it.title }}")
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    bookList.collectLatest { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {
                                Log.e(LOG, "Loading")
                                binding.progressBar.isVisible = true
                            }
                            is UiState.Success -> {
                                Log.e(LOG, "Success")
                                bookAdapter.submitList(bookAdapter.currentList + uiState.data.books)
                                viewModel.insertRecentSearch(uiState.data.query)   // 최근 검색어 추가
                                binding.progressBar.isVisible = false
                            }
                            is UiState.Error -> {
                                Log.e(LOG, "Error")
                                binding.progressBar.isVisible = false
                            }
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    errorMessage.collectLatest { errorMessage ->
                        Log.e(LOG, "error message : ${errorMessage.toString()}")
                        errorMessage?.let { message ->
                            showMessageDialog(message)
                            initErrorMessage()
                        }
                    }
                }
            }
        }
    }

    private fun showMessageDialog(message: String) {
        MessageDialog(
            message = message,
            secondButtonText = getString(R.string.button_confirm),
            isConfirmDialog = true
        ).run {
            show(supportFragmentManager, tag)
        }
    }

    private fun clearKeyboard(view: View) {
        view.clearFocus()
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initBookList() {
        bookAdapter.submitList(null)
        viewModel.lastBookList = null
    }

    private fun openBrowser(book: Book) {
        if (book.link.isNotEmpty()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(book.link)))
        }
    }

    private fun getSearchFilter(): String {
        return when (binding.chipGroupFilter.checkedChipId) {
            R.id.chip_publish_date -> SORT_OF_PUBLISH_DATE
            else -> SORT_OF_ACCURACY
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_bookmark -> {
                bookAdapter.submitList(null)
                bookmarkLauncher.launch(Intent(this, BookmarkActivity::class.java))
            }
            R.id.menu_recent_search -> {
                recentSearchLauncher.launch(Intent(this, RecentSearchActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }
}