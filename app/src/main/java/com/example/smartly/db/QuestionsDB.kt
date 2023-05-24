package com.example.smartly.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartly.dao.QuestionsDao
import com.example.smartly.model.ResultDbModel

@Database(entities = [ResultDbModel::class], version = 1, exportSchema = false)
abstract class QuestionsDB : RoomDatabase() {

    abstract fun getQuestionsDao(): QuestionsDao

}