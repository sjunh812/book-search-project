package org.sjhstudio.flow.bookproject.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.flow.bookproject.data.repository.BookRepositoryImpl
import org.sjhstudio.flow.bookproject.data.repository.BookmarkRepositoryImpl
import org.sjhstudio.flow.bookproject.data.repository.RecentSearchRepositoryImpl
import org.sjhstudio.flow.bookproject.domain.repository.BookRepository
import org.sjhstudio.flow.bookproject.domain.repository.BookmarkRepository
import org.sjhstudio.flow.bookproject.domain.repository.RecentSearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(bookRepositoryImpl: BookRepositoryImpl): BookRepository

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(bookmarkRepositoryImpl: BookmarkRepositoryImpl): BookmarkRepository

    @Binds
    @Singleton
    abstract fun bindRecentSearchRepository(recentSearchRepositoryImpl: RecentSearchRepositoryImpl): RecentSearchRepository
}