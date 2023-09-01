package com.app.dubovozkiapp.di

import android.content.Context
import androidx.room.Room
import com.app.dubovozkiapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    private lateinit var INSTANCE: AppDatabase

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        synchronized(AppDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dubovozki_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
        return INSTANCE
    }

}