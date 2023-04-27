package org.sjhstudio.flow.bookproject.domain

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.flow.bookproject.presentation.base.UiState

interface BookRepository {

    fun getBookList(query: String, start: Int): Flow<UiState<BookList>>
}