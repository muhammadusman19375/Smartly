package com.example.smartly.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Data")
data class ResultDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val question: String,
    val correct_answer: String,
    val user_selected_answer: String,
    val category: String,
    val earned_score: String,
    var isVisible: Boolean = false
)