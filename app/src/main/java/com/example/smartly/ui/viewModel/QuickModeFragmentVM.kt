package com.example.smartly.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartly.model.QuestionsResponseModel
import com.example.smartly.networking.NetworkResult
import com.example.smartly.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickModeFragmentVM @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _questionsResponse: MutableLiveData<NetworkResult<QuestionsResponseModel>> = MutableLiveData()
    var questionsResponse: LiveData<NetworkResult<QuestionsResponseModel>> = _questionsResponse

    fun getUnlimitedQuestion() = viewModelScope.launch {
        repository.getUnlimitedQuestion().collect { value ->
            _questionsResponse.value = value
        }
    }

    private var _remainingLife: MutableLiveData<Int> = MutableLiveData()
    val remainingLife: LiveData<Int> = _remainingLife

    fun remainingLife(remainingLife: Int){
        _remainingLife.value = remainingLife
    }

}