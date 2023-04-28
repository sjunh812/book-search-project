package org.sjhstudio.flow.bookproject.data.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.flow.bookproject.data.local.source.RecentSearchLocalDataSource
import org.sjhstudio.flow.bookproject.domain.model.RecentSearch
import org.sjhstudio.flow.bookproject.domain.repository.RecentSearchRepository
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val recentSearchLocalDataSource: RecentSearchLocalDataSource
) : RecentSearchRepository {

    override fun getRecentSearchList(): Flow<List<RecentSearch>> {
        return recentSearchLocalDataSource.getRecentSearchList()
    }

    override suspend fun insertRecentSearch(recentSearch: RecentSearch) {
        return recentSearchLocalDataSource.insertRecentSearch(recentSearch)
    }

    override suspend fun deleteRecentSearch(recentSearch: RecentSearch) {
        return recentSearchLocalDataSource.deleteRecentSearch(recentSearch)
    }

    override suspend fun deleteAll() {
        return recentSearchLocalDataSource.deleteAll()
    }
}