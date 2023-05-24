package com.example.smartly.repository

import com.example.smartly.dao.QuestionsDao
import com.example.smartly.model.QuestionsResponseModel
import com.example.smartly.model.ResultDbModel
import com.example.smartly.networking.BaseApiResponse
import com.example.smartly.networking.DataSource
import com.example.smartly.networking.NetworkResult
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(private val dataSource: DataSource, private val questionsDao: QuestionsDao) : BaseApiResponse() {

    suspend fun getQuestions(
        category: Int,
        difficulty: String,
        type: String
    ): Flow<NetworkResult<QuestionsResponseModel>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall { dataSource.getQuestions(category, difficulty, type) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUnlimitedQuestion(): Flow<NetworkResult<QuestionsResponseModel>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall { dataSource.getUnlimitedQuestion() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun saveData(question: ResultDbModel) {
        questionsDao.saveData(question)
    }

    fun getData(): Flow<List<ResultDbModel>> {
        return questionsDao.getData()
    }

}