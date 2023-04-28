package org.sjhstudio.flow.bookproject.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.flow.bookproject.domain.model.RecentSearch

interface RecentSearchRepository {

    fun getRecentSearchList(): Flow<List<RecentSearch>>

    suspend fun insertRecentSearch(recentSearch: RecentSearch)

    suspend fun deleteRecentSearch(recentSearch: RecentSearch)

    suspend fun deleteAll()
}