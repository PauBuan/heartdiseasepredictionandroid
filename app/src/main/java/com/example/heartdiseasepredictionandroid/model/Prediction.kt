package com.example.heartdiseasepredictionandroid.model

import com.google.gson.annotations.SerializedName

data class Prediction(
    val id: Int,
    @SerializedName("user_id") val userId: Int,
    val username: String,
    @SerializedName("prediction_data") val predictionData: String, // JSON string
    val timestamp: String,
    @SerializedName("model_type") val modelType: String
)