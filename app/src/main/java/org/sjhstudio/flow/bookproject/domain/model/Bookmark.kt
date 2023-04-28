package org.sjhstudio.flow.bookproject.domain.model

data class Bookmark(
    val isbn: String,
    val title: String,
    val link: String,
    val image: String,
    val author: String,
    val discount: String,
    val publisher: String,
    val publishDate: String,
    val description: String,
    val date: Long
)
