package com.example.heartdiseasepredictionandroid.repository

import com.example.heartdiseasepredictionandroid.api.KtorClient
import com.example.heartdiseasepredictionandroid.model.Prediction
import com.example.heartdiseasepredictionandroid.model.PredictionResult
import com.example.heartdiseasepredictionandroid.model.Record

class PredictionRepository {
    suspend fun predict(prediction: Prediction): Result<PredictionResult> {
        return KtorClient.predict(prediction)
    }

    suspend fun getRecords(userId: String): Result<List<Record>> {
        return KtorClient.getRecords(userId)
    }

    suspend fun getAllRecords(): Result<List<Record>> {
        return KtorClient.getAllRecords()
    }
}