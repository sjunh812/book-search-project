package org.sjhstudio.flow.bookproject.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.flow.bookproject.domain.model.BookList
import org.sjhstudio.flow.bookproject.domain.model.Bookmark
import org.sjhstudio.flow.bookproject.presentation.base.UiState

interface BookRepository {

    fun getBookList(
        query: String,
        start: Int,
        sort: String,
        bookmarkList: List<Bookmark>
    ): Flow<UiState<BookList>>
}