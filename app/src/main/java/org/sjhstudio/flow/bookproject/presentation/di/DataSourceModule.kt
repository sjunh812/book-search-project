package org.sjhstudio.flow.bookproject.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.flow.bookproject.data.remote.source.BookDataSource
import org.sjhstudio.flow.bookproject.data.remote.source.BookDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindBookDataSource(bookDataSourceImpl: BookDataSourceImpl): BookDataSource
}