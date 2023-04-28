package org.sjhstudio.flow.bookproject.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.flow.bookproject.domain.model.Bookmark

interface BookmarkRepository {

    fun getAllBookmarkList(): Flow<List<Bookmark>>

    fun getBookmarkList(): Flow<List<Bookmark>>

    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun deleteBookmark(bookmark: Bookmark)
}