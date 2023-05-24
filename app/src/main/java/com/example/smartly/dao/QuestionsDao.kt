package com.example.smartly.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartly.model.ResultDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveData(question: ResultDbModel)

    @Query("SELECT * FROM Data")
    fun getData(): Flow<List<ResultDbModel>>

}