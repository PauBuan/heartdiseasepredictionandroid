package com.example.heartdiseasepredictionandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heartdiseasepredictionandroid.model.User
import com.example.heartdiseasepredictionandroid.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    private val _registerResult = MutableLiveData<Result<Unit>>()
    val registerResult: LiveData<Result<Unit>> = _registerResult
    private val _loginResult = MutableLiveData<Result<Unit>>()
    val loginResult: LiveData<Result<Unit>> = _loginResult
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    fun register(name: String, username: String, password: String) {
        authRepository.register(name, username, password).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    _registerResult.postValue(Result.success(Unit))
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("AuthViewModel", "Registration failed: $errorBody, Code: ${response.code()}")
                    _registerResult.postValue(Result.failure(Exception("Registration failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("AuthViewModel", "Network error: ${t.message}")
                _registerResult.postValue(Result.failure(t))
            }
        })
    }

    fun login(username: String, password: String) {
        authRepository.login(username, password).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful && response.body() != null) {
                    _user.postValue(response.body())
                    _loginResult.postValue(Result.success(Unit))
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("AuthViewModel", "Login failed: $errorBody, Code: ${response.code()}")
                    _loginResult.postValue(Result.failure(Exception("Login failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("AuthViewModel", "Network error: ${t.message}")
                _loginResult.postValue(Result.failure(t))
            }
        })
    }
}