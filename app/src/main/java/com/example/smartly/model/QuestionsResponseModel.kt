package com.example.smartly.model

data class QuestionsResponseModel(
    val response_code: Int,
    val results: List<ResultModel>
)