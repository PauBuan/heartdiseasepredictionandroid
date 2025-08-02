package com.example.heartdiseasepredictionandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heartdiseasepredictionandroid.model.Prediction
import com.example.heartdiseasepredictionandroid.model.PredictionResult
import com.example.heartdiseasepredictionandroid.repository.PredictionRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictionViewModel : ViewModel() {
    private val predictionRepository = PredictionRepository()
    private val _predictions = MutableLiveData<Result<List<Prediction>>>()
    val predictions: LiveData<Result<List<Prediction>>> = _predictions
    private val _predictionResult = MutableLiveData<Result<PredictionResult>>()
    val predictionResult: LiveData<Result<PredictionResult>> = _predictionResult

    fun getPredictions(userId: String) {
        predictionRepository.getPredictions(userId).enqueue(object : Callback<List<Prediction>> {
            override fun onResponse(call: Call<List<Prediction>>, response: Response<List<Prediction>>) {
                if (response.isSuccessful) {
                    _predictions.postValue(Result.success(response.body() ?: emptyList()))
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("PredictionViewModel", "Failed to load predictions: $errorBody")
                    _predictions.postValue(Result.failure(Exception("Failed to load predictions: $errorBody")))
                }
            }

            override fun onFailure(call: Call<List<Prediction>>, t: Throwable) {
                Log.e("PredictionViewModel", "Network error: ${t.message}")
                _predictions.postValue(Result.failure(t))
            }
        })
    }

    fun predict(
        userId: String,
        modelType: String,
        age: Float,
        trestbps: Float,
        chol: Float,
        thalach: Float,
        oldpeak: Float,
        ca: Int,
        sex: Int,
        cp: Int,
        exang: Int,
        slope: Int,
        thal: Int
    ) {
        predictionRepository.predict(userId, modelType, age, trestbps, chol, thalach, oldpeak, ca, sex, cp, exang, slope, thal)
            .enqueue(object : Callback<PredictionResult> {
                override fun onResponse(call: Call<PredictionResult>, response: Response<PredictionResult>) {
                    if (response.isSuccessful) {
                        _predictionResult.postValue(Result.success(response.body()) as Result<PredictionResult>?)
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        Log.e("PredictionViewModel", "Prediction failed: $errorBody")
                        _predictionResult.postValue(Result.failure(Exception("Prediction failed: $errorBody")))
                    }
                }

                override fun onFailure(call: Call<PredictionResult>, t: Throwable) {
                    Log.e("PredictionViewModel", "Network error: ${t.message}")
                    _predictionResult.postValue(Result.failure(t))
                }
            })
    }
}