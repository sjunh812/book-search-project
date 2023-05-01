package org.sjhstudio.flow.bookproject.presentation.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.sjhstudio.flow.bookproject.R
import org.sjhstudio.flow.bookproject.databinding.ActivityBookmarkBinding
import org.sjhstudio.flow.bookproject.domain.model.Bookmark
import org.sjhstudio.flow.bookproject.presentation.base.BaseActivity
import org.sjhstudio.flow.bookproject.presentation.ui.adapter.BookmarkAdapter
import org.sjhstudio.flow.bookproject.presentation.ui.common.MessageDialog
import org.sjhstudio.flow.bookproject.presentation.ui.viewmodel.BookmarkViewModel

/**
 * MainActivity 에서 북마크 DB 데이터를 가져올지? (x)
 * BookmarkRepository 에 접근하여 북마크 DB 데이터 가져올지? (o) → 조회 외에도 삭제 구현이 필요함.
 */
@AndroidEntryPoint
class BookmarkActivity : BaseActivity<ActivityBookmarkBinding>(R.layout.activity_bookmark) {

    private val viewModel: BookmarkViewModel by viewModels()
    private val bookmarkAdapter by lazy {
        BookmarkAdapter(
            onClickBook = { bookmark ->
                openBrowser(bookmark)
            },
            onClickBookmark = { bookmark ->
                showDeleteBookmarkDialog(bookmark)
            }
        )
    }

    companion object {
        private const val LOG = "BookmarkActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        with(binding) {
            supportActionBar?.apply {
                title = getString(R.string.label_bookmark)
                setDisplayHomeAsUpEnabled(true)
            }

            rvBookmark.adapter = bookmarkAdapter
        }
    }

    private fun observeData() {
        with(viewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    bookmarkList.collectLatest { list ->
                        Log.e(LOG, "bookmark list : ${list.map { it.title }}")
                        bookmarkAdapter.submitList(list)
                    }
                }
            }
        }
    }

    private fun openBrowser(bookmark: Bookmark) {
        if (bookmark.link.isNotEmpty()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(bookmark.link)))
        }
    }

    private fun showDeleteBookmarkDialog(bookmark: Bookmark) {
        MessageDialog(
            message = "북마크를 삭제하시겠습니까?",
            firstButtonText = getString(R.string.button_cancel),
            secondButtonText = getString(R.string.button_delete),
            clickedSecondButton = {
                viewModel.deleteBookmark(bookmark)
            }
        ).run {
            show(supportFragmentManager, tag)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}