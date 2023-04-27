package org.sjhstudio.flow.bookproject.domain

data class BookList(
    val query: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val books: List<Book>
)

data class Book(
    val title: String,
    val image: String,
    val author: String,
    val discount: String,
    val publisher: String,
    val publishDate: String,
    val isbn: String,
    val description: String,
    var isExpand: Boolean = false
)