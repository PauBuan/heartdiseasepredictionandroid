package com.example.heartdiseasepredictionandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val id: String,
    val username: String,
    val name: String,
    val message: String
)