package com.example.smartly.networking

import javax.inject.Inject

class DataSource @Inject constructor(private val retrofitService: RetrofitService) {

    suspend fun getQuestions(
        category: Int,
        difficulty: String,
        type: String
    ) = retrofitService.getQuestions(category, difficulty, type)

    suspend fun getUnlimitedQuestion() = retrofitService.getUnlimitedQuestion()

}