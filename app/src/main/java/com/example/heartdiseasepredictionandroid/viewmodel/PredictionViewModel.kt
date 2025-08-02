package com.example.heartdiseasepredictionandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heartdiseasepredictionandroid.model.Prediction
import com.example.heartdiseasepredictionandroid.model.PredictionResult
import com.example.heartdiseasepredictionandroid.model.Record
import com.example.heartdiseasepredictionandroid.repository.PredictionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PredictionViewModel : ViewModel() {
    private val predictionRepository = PredictionRepository()

    private val _predictionResult = MutableStateFlow<Result<PredictionResult>?>(null)
    val predictionResult: StateFlow<Result<PredictionResult>?> = _predictionResult

    private val _records = MutableStateFlow<Result<List<Record>>?>(null)
    val records: StateFlow<Result<List<Record>>?> = _records

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _modelType = MutableStateFlow<String?>(null)
    val modelType: StateFlow<String?> = _modelType

    fun predict(prediction: Prediction) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _predictionResult.value = predictionRepository.predict(prediction)
            _isLoading.value = false
        }
    }

    fun getRecords(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _records.value = predictionRepository.getRecords(userId)
            _isLoading.value = false
        }
    }

    fun fetchAllRecords() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _records.value = predictionRepository.getAllRecords()
            _records.value?.fold(
                onSuccess = { /* Success, records updated */ },
                onFailure = { e -> _errorMessage.value = e.message }
            )
            _isLoading.value = false
        }
    }

    fun setModelType(modelType: String) {
        _modelType.value = modelType
    }

    fun clearPredictionResult() {
        _predictionResult.value = null
    }
}