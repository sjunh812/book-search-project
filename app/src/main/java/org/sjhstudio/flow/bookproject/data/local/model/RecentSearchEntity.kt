package org.sjhstudio.flow.bookproject.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.sjhstudio.flow.bookproject.domain.model.RecentSearch

@Entity(tableName = "recentSearchEntity")
data class RecentSearchEntity(
    @PrimaryKey val word: String,
    val date: Long
) {

    fun toRecentSearch(): RecentSearch {
        return RecentSearch(word, date)
    }
}