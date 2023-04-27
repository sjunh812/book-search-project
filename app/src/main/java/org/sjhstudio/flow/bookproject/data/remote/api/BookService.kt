package org.sjhstudio.flow.bookproject.data.remote.api

import org.sjhstudio.flow.bookproject.data.remote.model.BookListEntity
import org.sjhstudio.flow.bookproject.presentation.util.Constants.SORT_OF_ACCURACY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {

    @GET("/v1/search/book.json")
    suspend fun getBookList(
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("sort") sort: String = SORT_OF_ACCURACY,
        @Query("display") display: Int = 20
    ): Response<BookListEntity?>
}