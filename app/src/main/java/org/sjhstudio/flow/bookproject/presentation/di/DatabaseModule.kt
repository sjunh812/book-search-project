package org.sjhstudio.flow.bookproject.presentation.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.flow.bookproject.data.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "BookSearch.db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        synchronized(this) {
            return Room.databaseBuilder(
                context = context,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}