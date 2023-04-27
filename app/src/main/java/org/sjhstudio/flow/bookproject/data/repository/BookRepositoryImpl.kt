package org.sjhstudio.flow.bookproject.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.sjhstudio.flow.bookproject.data.exception.ClientErrorException
import org.sjhstudio.flow.bookproject.data.remote.source.BookDataSource
import org.sjhstudio.flow.bookproject.domain.model.BookList
import org.sjhstudio.flow.bookproject.domain.repository.BookRepository
import org.sjhstudio.flow.bookproject.presentation.base.UiState
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookDataSource: BookDataSource
) : BookRepository {

    override fun getBookList(query: String, start: Int): Flow<UiState<BookList>> =
        flow {
            val bookListEntity = bookDataSource.getBookList(query, start)
            emit(UiState.Success(bookListEntity.toBookList(query)))
        }.buffer().catch { e ->
            throw ClientErrorException(e.message)
        }
}