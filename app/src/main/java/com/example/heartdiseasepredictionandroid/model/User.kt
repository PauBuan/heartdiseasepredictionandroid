package com.example.heartdiseasepredictionandroid.model

data class User(
    val id: Int? = null, // Add id to match Flask API response
    val username: String,
    val name: String? = null, // Add name for registration
    val password: String? = null // Password may not be returned in login response
)