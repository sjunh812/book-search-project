package org.sjhstudio.flow.bookproject.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.flow.bookproject.domain.model.BookList
import org.sjhstudio.flow.bookproject.presentation.base.UiState

interface BookRepository {

    fun getBookList(query: String, start: Int): Flow<UiState<BookList>>
}