package com.example.heartdiseasepredictionandroid.repository

import com.example.heartdiseasepredictionandroid.api.KtorClient
import com.example.heartdiseasepredictionandroid.model.LoginResponse
import com.example.heartdiseasepredictionandroid.model.User

class AuthRepository {
    suspend fun register(name: String, username: String, password: String, specialty: String): Result<User> {
        return KtorClient.register(name, username, password, specialty)
    }

    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return KtorClient.login(username, password)
    }
}