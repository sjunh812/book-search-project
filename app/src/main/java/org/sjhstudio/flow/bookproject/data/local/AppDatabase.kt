package org.sjhstudio.flow.bookproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.sjhstudio.flow.bookproject.data.local.dao.BookmarkDao
import org.sjhstudio.flow.bookproject.data.local.dao.RecentSearchDao
import org.sjhstudio.flow.bookproject.data.local.model.BookmarkEntity
import org.sjhstudio.flow.bookproject.data.local.model.RecentSearchEntity

@Database(entities = [BookmarkEntity::class, RecentSearchEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    abstract fun recentSearchDao(): RecentSearchDao
}