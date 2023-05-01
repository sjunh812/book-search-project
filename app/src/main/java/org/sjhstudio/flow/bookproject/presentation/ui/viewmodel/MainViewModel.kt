package org.sjhstudio.flow.bookproject.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.flow.bookproject.domain.model.Book
import org.sjhstudio.flow.bookproject.domain.model.BookList
import org.sjhstudio.flow.bookproject.domain.model.RecentSearch
import org.sjhstudio.flow.bookproject.domain.repository.BookRepository
import org.sjhstudio.flow.bookproject.domain.repository.BookmarkRepository
import org.sjhstudio.flow.bookproject.domain.repository.RecentSearchRepository
import org.sjhstudio.flow.bookproject.presentation.base.UiState
import org.sjhstudio.flow.bookproject.presentation.base.successOrNull
import org.sjhstudio.flow.bookproject.presentation.exception.EmptyBodyException
import org.sjhstudio.flow.bookproject.presentation.exception.NetworkErrorException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val recentSearchRepository: RecentSearchRepository
) : ViewModel() {

    val bookmarkList = bookmarkRepository.getAllBookmarkList().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,   // collector 가 없어도 데이터 방출
        initialValue = emptyList()
    )

    private var _bookList = MutableSharedFlow<UiState<BookList>>()
    val bookList = _bookList.asSharedFlow()

    private var _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    var lastBookList: BookList? = null  // 마지막 검색결과

    companion object {
        private const val LOG = "MainViewModel"
    }

    fun searchBook(query: String, start: Int, sort: String) = viewModelScope.launch {
        Log.e(LOG, "query : $query, start : $start, sort : $sort")
        _bookList.emit(UiState.Loading)

        bookRepository.getBookList(query, start, sort, bookmarkList.value)
            .onStart { }
            .onCompletion { }
            .catch { e ->
                Log.e(LOG, e.message ?: e.toString())
                e.printStackTrace()

                when (e) {
                    is NetworkErrorException -> _errorMessage.emit("서버 상태가 원할하지 않습니다.")
                    is EmptyBodyException -> _errorMessage.emit("예상치 못한 오류가 발생하였습니다.")
                    else -> _errorMessage.emit("네트워크 연결이 원할하지 않습니다.")
                }

                _bookList.emit(UiState.Error(e))
            }
            .collectLatest { uiState ->
                _bookList.emit(uiState)

                uiState.successOrNull()?.let { data ->
                    lastBookList = data
                }
            }
    }

    fun insertBookmark(book: Book) = viewModelScope.launch {
        bookmarkRepository.insertBookmark(book.toBookmark())
    }

    fun deleteBookmark(book: Book) = viewModelScope.launch {
        bookmarkRepository.deleteBookmark(book.toBookmark())
    }

    fun insertRecentSearch(word: String) = viewModelScope.launch {
        recentSearchRepository.insertRecentSearch(RecentSearch(word, Date().time))
    }

    fun initErrorMessage() = viewModelScope.launch {
        _errorMessage.emit(null)
    }
}