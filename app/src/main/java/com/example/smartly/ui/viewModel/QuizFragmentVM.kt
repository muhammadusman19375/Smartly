package com.example.smartly.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartly.model.QuestionsResponseModel
import com.example.smartly.model.ResultDbModel
import com.example.smartly.networking.NetworkResult
import com.example.smartly.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizFragmentVM @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _questionsResponse: MutableLiveData<NetworkResult<QuestionsResponseModel>> = MutableLiveData()
    var questionsResponse: LiveData<NetworkResult<QuestionsResponseModel>> = _questionsResponse

    fun getQuestions(
        category: Int,
        difficulty: String,
        type: String
    ) = viewModelScope.launch {
        repository.getQuestions(category, difficulty, type).collect { value ->
            _questionsResponse.value = value
        }
    }

    fun saveData(question: ResultDbModel) {
        viewModelScope.launch {
            repository.saveData(question)
        }
    }

}