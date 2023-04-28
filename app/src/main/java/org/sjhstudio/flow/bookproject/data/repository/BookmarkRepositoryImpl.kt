package org.sjhstudio.flow.bookproject.data.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.flow.bookproject.data.local.source.BookmarkLocalDataSource
import org.sjhstudio.flow.bookproject.domain.model.Bookmark
import org.sjhstudio.flow.bookproject.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : BookmarkRepository {

    override fun getAllBookmarkList(): Flow<List<Bookmark>> {
        return bookmarkLocalDataSource.getAllBookmarkList()
    }

    override fun getBookmarkList(): Flow<List<Bookmark>> {
        return bookmarkLocalDataSource.getBookmarkList()
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        return bookmarkLocalDataSource.insertBookmark(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        return bookmarkLocalDataSource.deleteBookmark(bookmark)
    }
}