package org.sjhstudio.flow.bookproject.domain.model

import java.util.*

data class BookList(
    val query: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val books: List<Book>
)

data class Book(
    val title: String,
    val link: String,
    val image: String,
    val author: String,
    val discount: String,
    val publisher: String,
    val publishDate: String,
    val isbn: String,
    val description: String,
    var isExpand: Boolean = false,   // 상세보기 여부
    var isBookmark: Boolean = false // 북마크 여부
) {

    fun toBookmark(): Bookmark {
        return Bookmark(
            isbn,
            title,
            link,
            image,
            author,
            discount,
            publisher,
            publishDate,
            description,
            date = Date().time
        )
    }
}