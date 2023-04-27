package org.sjhstudio.flow.bookproject.presentation.ui.view

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
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
import org.sjhstudio.flow.bookproject.presentation.ui.viewmodel.MainViewModel
import org.sjhstudio.flow.bookproject.presentation.ui.adapter.BookAdapter

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

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
            etSearch.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.text?.also { bookName ->
                        if (bookName.trim().isNotEmpty()) {
                            bookAdapter.submitList(null)
                            viewModel.searchBook(bookName.toString(), 1)
                        }
                    }?.run {
                        clear()
                    }
                }
                false
            }

            rvBook.apply {
                adapter = bookAdapter
                itemAnimator = null

                addOnScrollListener(object: RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (bookAdapter.itemCount == 0) return

                        val linearLayoutManager = layoutManager as LinearLayoutManager
                        val lastPosition = linearLayoutManager.itemCount - 1
                        val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()

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
                        Log.e(LOG, "$uiState")
                        when (uiState) {
                            is UiState.Loading -> {
                                binding.progressBar.isVisible = true
                            }
                            is UiState.Success -> {
                                val currentList = bookAdapter.currentList
                                bookAdapter.submitList(currentList + uiState.data.books)
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
                    }
                }
            }
        }
    }
}