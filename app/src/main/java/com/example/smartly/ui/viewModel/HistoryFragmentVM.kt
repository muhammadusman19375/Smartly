package com.example.smartly.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartly.model.ResultDbModel
import com.example.smartly.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryFragmentVM @Inject constructor(private val repository: Repository): ViewModel() {

    private val _getData: MutableLiveData<List<ResultDbModel>> = MutableLiveData()
    val getData: LiveData<List<ResultDbModel>> = _getData

    fun getData() = viewModelScope.launch {
        repository.getData().collect { dataList ->
            _getData.value = dataList
        }
    }

}