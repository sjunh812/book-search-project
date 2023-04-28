package org.sjhstudio.flow.bookproject.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.flow.bookproject.data.local.source.BookmarkLocalDataSource
import org.sjhstudio.flow.bookproject.data.local.source.BookmarkLocalDataSourceImpl
import org.sjhstudio.flow.bookproject.data.local.source.RecentSearchLocalDataSource
import org.sjhstudio.flow.bookproject.data.local.source.RecentSearchLocalDataSourceImpl
import org.sjhstudio.flow.bookproject.data.remote.source.BookDataSource
import org.sjhstudio.flow.bookproject.data.remote.source.BookDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindBookDataSource(bookDataSourceImpl: BookDataSourceImpl): BookDataSource

    @Binds
    @Singleton
    abstract fun bindBookmarkLocalDataSource(bookmarkLocalDataSourceImpl: BookmarkLocalDataSourceImpl): BookmarkLocalDataSource

    @Binds
    @Singleton
    abstract fun bindRecentSearchLocalDataSource(recentSearchLocalDataSourceImpl: RecentSearchLocalDataSourceImpl): RecentSearchLocalDataSource
}