package com.example.heartdiseasepredictionandroid.model

import com.google.gson.annotations.SerializedName

data class PredictionResult(
    val cluster: Int,
    @SerializedName("risk_level") val riskLevel: String,
    val message: String,
    val input: InputData,
    @SerializedName("model_type") val modelType: String
)

data class InputData(
    val age: Float,
    val trestbps: Float,
    val chol: Float,
    val thalach: Float,
    val oldpeak: Float,
    val ca: Int,
    val sex: Int,
    val cp: Int,
    val exang: Int,
    val slope: Int,
    val thal: Int
)