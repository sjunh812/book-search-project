package org.sjhstudio.flow.bookproject.presentation.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.sjhstudio.flow.bookproject.R
import org.sjhstudio.flow.bookproject.databinding.ActivityMainBinding
import org.sjhstudio.flow.bookproject.presentation.base.BaseActivity
import org.sjhstudio.flow.bookproject.presentation.base.UiState
import org.sjhstudio.flow.bookproject.presentation.ui.adapter.BookAdapter
import org.sjhstudio.flow.bookproject.presentation.ui.viewmodel.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var imm: InputMethodManager

    private val viewModel: MainViewModel by viewModels()
    private val bookAdapter by lazy { BookAdapter() }

    companion object {
        private const val LOG = "MainActivity"
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
                            bookAdapter.submitList(null)    // 어뎁터 내 리스트 초기화
                            viewModel.searchBook(bookName.toString(), 1)
                        }
                    }?.run {
                        clear() // 검색창 초기화
                        clearKeyboard(etSearch) // 키보드 숨기기
                    }
                }
                false
            }

            rvBook.apply {
                adapter = bookAdapter
                itemAnimator = null

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (bookAdapter.itemCount == 0) return  // 어뎁터 내 리스트가 초기화 되는경우 예외처리

                        val linearLayoutManager = layoutManager as LinearLayoutManager
                        val lastPosition = linearLayoutManager.itemCount - 1
                        val lastVisiblePosition =
                            linearLayoutManager.findLastCompletelyVisibleItemPosition()

                        if (lastPosition == lastVisiblePosition) {
                            viewModel.lastBookList?.let { last ->
                                val end = last.start + last.display - 1
                                if (end < last.total) {
                                    viewModel.searchBook(last.query, end + 1)
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
                    bookList.collectLatest { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {
                                binding.progressBar.isVisible = true
                            }
                            is UiState.Success -> {
                                bookAdapter.submitList(bookAdapter.currentList + uiState.data.books)
                                binding.progressBar.isVisible = false
                            }
                            is UiState.Error -> {
                                binding.progressBar.isVisible = true
                            }
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    errorMessage.collectLatest { errorMessage ->
                        Log.e(LOG, errorMessage.toString())
                        // todo. 메시지 다이얼로그
                    }
                }
            }
        }
    }

    private fun clearKeyboard(view: View) {
        view.clearFocus()
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}