package org.sjhstudio.flow.bookproject.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.sjhstudio.flow.bookproject.data.local.model.BookmarkEntity

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmarkEntity")
    fun getAllBookmarkList(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarkEntity ORDER BY date DESC LIMIT 10 ")
    fun getBookmarkList(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)
}