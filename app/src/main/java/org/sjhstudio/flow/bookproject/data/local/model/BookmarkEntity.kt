package org.sjhstudio.flow.bookproject.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.sjhstudio.flow.bookproject.domain.model.Bookmark

@Entity(tableName = "bookmarkEntity")
data class BookmarkEntity(
    @PrimaryKey val isbn: String,
    val title: String,
    val link: String,
    val image: String,
    val author: String,
    val discount: String,
    val publisher: String,
    val publishDate: String,
    val description: String,
    val date: Long
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
            date
        )
    }
}
