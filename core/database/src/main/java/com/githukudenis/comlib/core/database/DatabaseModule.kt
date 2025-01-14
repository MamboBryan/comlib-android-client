package com.githukudenis.comlib.core.database

import android.content.Context
import androidx.room.Room
import com.githukudenis.comlib.core.database.dao.BookMilestoneDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext
        context: Context
    ): ComlibDatabase {
        return Room.databaseBuilder(
            context, ComlibDatabase::class.java, "comlib"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBookMilestoneDao(
        database: ComlibDatabase
    ): BookMilestoneDao {
        return database.bookMilestoneDao()
    }
}