package com.example.heartdiseasepredictionandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class PredictionResult(
    val cluster: Int,
    val risk_level: String,
    val message: String,
    val input: Map<String, String>,
    val model_type: String
)