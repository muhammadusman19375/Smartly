package com.example.smartly.networking

import com.example.smartly.model.QuestionsResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/api.php?amount=10")
    suspend fun getQuestions(
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String,
    ):Response<QuestionsResponseModel>


    @GET("/api.php?amount=50")
    suspend fun getUnlimitedQuestion(): Response<QuestionsResponseModel>

}