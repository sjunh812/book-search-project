package org.sjhstudio.flow.bookproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sjhstudio.flow.bookproject.domain.model.Bookmark
import org.sjhstudio.flow.bookproject.domain.repository.BookmarkRepository
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    val bookmarkList = bookmarkRepository.getBookmarkList().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun deleteBookmark(bookmark: Bookmark) = viewModelScope.launch {
        bookmarkRepository.deleteBookmark(bookmark)
    }
}