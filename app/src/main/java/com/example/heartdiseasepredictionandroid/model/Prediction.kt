package com.example.heartdiseasepredictionandroid.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Prediction(
    val userId: String,
    val modelType: String,
    val age: String,
    val trestbps: String,
    val chol: String,
    val thalach: String,
    val oldpeak: String,
    val ca: String,
    val sex: String,
    val cp: String,
    val exang: String,
    val slope: String,
    val thal: String
)