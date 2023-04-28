package org.sjhstudio.flow.bookproject.data.local.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.sjhstudio.flow.bookproject.data.local.AppDatabase
import org.sjhstudio.flow.bookproject.data.local.mapperToRecentSearchEntity
import org.sjhstudio.flow.bookproject.data.local.mapperToRecentSearchList
import org.sjhstudio.flow.bookproject.domain.model.RecentSearch
import javax.inject.Inject

interface RecentSearchLocalDataSource {

    fun getRecentSearchList(): Flow<List<RecentSearch>>

    suspend fun insertRecentSearch(recentSearch: RecentSearch)

    suspend fun deleteRecentSearch(recentSearch: RecentSearch)

    suspend fun deleteAll()
}

class RecentSearchLocalDataSourceImpl @Inject constructor(
    private val database: AppDatabase
) : RecentSearchLocalDataSource {

    override fun getRecentSearchList(): Flow<List<RecentSearch>> {
        return database.recentSearchDao().getRecentSearchList()
            .map { entities ->
                mapperToRecentSearchList(entities)
            }
    }

    override suspend fun insertRecentSearch(recentSearch: RecentSearch) {
        return database.recentSearchDao()
            .insertRecentSearch(mapperToRecentSearchEntity(recentSearch))
    }

    override suspend fun deleteRecentSearch(recentSearch: RecentSearch) {
        return database.recentSearchDao()
            .deleteRecentSearch(mapperToRecentSearchEntity(recentSearch))
    }

    override suspend fun deleteAll() {
        return database.recentSearchDao().deleteAll()
    }
}