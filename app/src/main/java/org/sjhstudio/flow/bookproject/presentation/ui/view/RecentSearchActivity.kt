package org.sjhstudio.flow.bookproject.presentation.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.sjhstudio.flow.bookproject.R
import org.sjhstudio.flow.bookproject.databinding.ActivityRecentSearchBinding
import org.sjhstudio.flow.bookproject.domain.model.RecentSearch
import org.sjhstudio.flow.bookproject.presentation.base.BaseActivity
import org.sjhstudio.flow.bookproject.presentation.ui.adapter.RecentSearchAdapter
import org.sjhstudio.flow.bookproject.presentation.ui.common.MessageDialog
import org.sjhstudio.flow.bookproject.presentation.ui.viewmodel.RecentSearchViewModel

@AndroidEntryPoint
class RecentSearchActivity :
    BaseActivity<ActivityRecentSearchBinding>(R.layout.activity_recent_search) {

    private val viewModel: RecentSearchViewModel by viewModels()
    private val recentSearchAdapter by lazy {
        RecentSearchAdapter(
            onClickItem = { recentSearch ->
                navigateToMainActivity(recentSearch)
            },
            onDelete = { recentSearch ->
                viewModel.deleteRecentSearch(recentSearch)
            }
        )
    }

    companion object {
        private const val LOG = "RecentSearchActivity"
        const val EXTRA_QUERY = "query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        with(binding) {
            supportActionBar?.apply {
                title = getString(R.string.label_recent_search)
                setDisplayHomeAsUpEnabled(true)
            }
            rvRecentSearch.adapter = recentSearchAdapter
        }
    }

    private fun observeData() {
        with(viewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    recentSearchList.collectLatest { list ->
                        Log.e(LOG, "recent search list : $list")
                        recentSearchAdapter.submitList(list)
                    }
                }
            }
        }
    }

    private fun navigateToMainActivity(recentSearch: RecentSearch) {
        val intent = Intent(this, MainActivity::class.java)
            .apply {
                putExtra(EXTRA_QUERY, recentSearch.word)
            }
        Log.e(LOG, "send query : ${recentSearch.word}")
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun showDeleteRecentSearchDialog() {
        MessageDialog(
            message = "최근 검색어를 모두 삭제하시겠습니까?",
            firstButtonText = getString(R.string.button_cancel),
            secondButtonText = getString(R.string.button_delete),
            clickedSecondButton = {
                viewModel.deleteAll()
            }
        ).run {
            show(supportFragmentManager, tag)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recent_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_delete_all -> showDeleteRecentSearchDialog()
        }
        return super.onOptionsItemSelected(item)
    }
}