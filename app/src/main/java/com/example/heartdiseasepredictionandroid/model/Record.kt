package com.example.heartdiseasepredictionandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class Record(
    val id: Int,
    val user_id: String,
    val username: String,
    val prediction_data: String,
    val timestamp: String,
    val model_type: String
)