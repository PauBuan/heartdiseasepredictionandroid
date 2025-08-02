package com.example.heartdiseasepredictionandroid.repository

import com.example.heartdiseasepredictionandroid.api.RetrofitClient
import com.example.heartdiseasepredictionandroid.model.User
import retrofit2.Call

class AuthRepository {
    fun register(name: String, username: String, password: String): Call<User> {
        return RetrofitClient.instance.register(name, username, password)
    }

    fun login(username: String, password: String): Call<User> {
        return RetrofitClient.instance.login(username, password)
    }
}