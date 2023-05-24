package com.example.smartly.dbModule

import android.content.Context
import androidx.room.Room
import com.example.smartly.dao.QuestionsDao
import com.example.smartly.db.QuestionsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuestionsDatabase {

    @Singleton
    @Provides
    fun providesQuestionsDB(@ApplicationContext context: Context): QuestionsDB {
        return Room.databaseBuilder(
            context,
            QuestionsDB::class.java,
            "QuestionsDB"
        ).build()
    }

    @Singleton
    @Provides
    fun providesQuestionsDao(db: QuestionsDB): QuestionsDao {
        return db.getQuestionsDao()
    }

}