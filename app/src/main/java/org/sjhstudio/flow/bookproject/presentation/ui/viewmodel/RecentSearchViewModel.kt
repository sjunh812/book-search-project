package org.sjhstudio.flow.bookproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sjhstudio.flow.bookproject.domain.model.RecentSearch
import org.sjhstudio.flow.bookproject.domain.repository.RecentSearchRepository
import javax.inject.Inject

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository
) : ViewModel() {

    val recentSearchList = recentSearchRepository.getRecentSearchList().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun deleteRecentSearch(recentSearch: RecentSearch) = viewModelScope.launch {
        recentSearchRepository.deleteRecentSearch(recentSearch)
    }

    fun deleteAll() = viewModelScope.launch {
        recentSearchRepository.deleteAll()
    }
}