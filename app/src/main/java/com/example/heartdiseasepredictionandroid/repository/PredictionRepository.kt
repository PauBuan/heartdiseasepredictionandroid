package com.example.heartdiseasepredictionandroid.repository

import com.example.heartdiseasepredictionandroid.api.RetrofitClient
import com.example.heartdiseasepredictionandroid.model.Prediction
import com.example.heartdiseasepredictionandroid.model.PredictionResult
import retrofit2.Call

class PredictionRepository {
    fun getPredictions(userId: String): Call<List<Prediction>> {
        return RetrofitClient.instance.getPredictions(userId)
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
    ): Call<PredictionResult> {
        return RetrofitClient.instance.predict(userId, modelType, age, trestbps, chol, thalach, oldpeak, ca, sex, cp, exang, slope, thal)
    }
}