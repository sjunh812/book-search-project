package org.sjhstudio.flow.bookproject.data.remote.source

import org.sjhstudio.flow.bookproject.data.remote.api.BookService
import org.sjhstudio.flow.bookproject.data.remote.model.BookListEntity
import org.sjhstudio.flow.bookproject.presentation.exception.EmptyBodyException
import org.sjhstudio.flow.bookproject.presentation.exception.NetworkErrorException
import javax.inject.Inject

interface BookDataSource {

    suspend fun getBookList(query: String, start: Int): BookListEntity
}

class BookDataSourceImpl @Inject constructor(
    private val bookService: BookService
) : BookDataSource {

    override suspend fun getBookList(query: String, start: Int): BookListEntity {
        val response = bookService.getBookList(query, start)

        if (response.isSuccessful) {
            return response.body()
                ?: throw EmptyBodyException("[${response.code()}] : ${response.raw()}")
        } else {
            throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
        }
    }
}