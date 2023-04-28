package org.sjhstudio.flow.bookproject.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.sjhstudio.flow.bookproject.data.local.model.RecentSearchEntity

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM recentSearchEntity ORDER BY date DESC LIMIT 10")
    fun getRecentSearchList(): Flow<List<RecentSearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(recentSearch: RecentSearchEntity)

    @Delete
    suspend fun deleteRecentSearch(recentSearch: RecentSearchEntity)

    @Query("DELETE FROM recentSearchEntity")
    suspend fun deleteAll()
}