package org.sjhstudio.flow.bookproject.data.local.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.sjhstudio.flow.bookproject.data.local.AppDatabase
import org.sjhstudio.flow.bookproject.data.local.mapperToBookmarkEntity
import org.sjhstudio.flow.bookproject.data.local.mapperToBookmarkList
import org.sjhstudio.flow.bookproject.domain.model.Bookmark
import javax.inject.Inject

interface BookmarkLocalDataSource {

    fun getAllBookmarkList(): Flow<List<Bookmark>>

    fun getBookmarkList(): Flow<List<Bookmark>>

    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun deleteBookmark(bookmark: Bookmark)
}

class BookmarkLocalDataSourceImpl @Inject constructor(
    private val database: AppDatabase
) : BookmarkLocalDataSource {

    override fun getAllBookmarkList(): Flow<List<Bookmark>> {
        return database.bookmarkDao().getAllBookmarkList()
            .map { entities ->
                mapperToBookmarkList(entities)
            }
    }

    override fun getBookmarkList(): Flow<List<Bookmark>> {
        return database.bookmarkDao().getBookmarkList()
            .map { entities ->
                mapperToBookmarkList(entities)
            }
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        return database.bookmarkDao().insertBookmark(mapperToBookmarkEntity(bookmark))
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        return database.bookmarkDao().deleteBookmark(mapperToBookmarkEntity(bookmark))
    }
}