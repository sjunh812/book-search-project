package org.sjhstudio.flow.bookproject.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.sjhstudio.flow.bookproject.domain.model.Book
import org.sjhstudio.flow.bookproject.domain.model.BookList

@JsonClass(generateAdapter = true)
data class BookListEntity(
    @Json(name = "lastBuildDate") val lastBuildDate: String,
    @Json(name = "total") val total: Int,
    @Json(name = "start") val start: Int,
    @Json(name = "display") val display: Int,
    @Json(name = "items") val items: List<BookEntity>
) {

    fun toBookList(query: String): BookList {
        return BookList(
            query = query,
            total = total,
            start = start,
            display = display,
            books = items.map { entity ->
                Book(
                    title = entity.title,
                    image = entity.image,
                    author = entity.author,
                    discount = entity.discount,
                    publisher = entity.publisher,
                    publishDate = entity.publishDate,
                    isbn = entity.isbn,
                    description = entity.description
                )
            }
        )
    }
}

@JsonClass(generateAdapter = true)
data class BookEntity(
    @Json(name = "title") val title: String,
    @Json(name = "link") val link: String,
    @Json(name = "image") val image: String,
    @Json(name = "author") val author: String,
    @Json(name = "discount") val discount: String,
    @Json(name = "publisher") val publisher: String,
    @Json(name = "pubdate") val publishDate: String,
    @Json(name = "isbn") val isbn: String,  // 도서번호(고유값)
    @Json(name = "description") val description: String
)