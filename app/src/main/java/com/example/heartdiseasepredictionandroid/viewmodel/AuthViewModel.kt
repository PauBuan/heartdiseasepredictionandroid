package com.example.heartdiseasepredictionandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heartdiseasepredictionandroid.api.KtorClient
import com.example.heartdiseasepredictionandroid.model.LoginResponse
import com.example.heartdiseasepredictionandroid.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun register(name: String, username: String, password: String, specialty: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = KtorClient.register(name, username, password, specialty)
                response.fold(
                    onSuccess = { user ->
                        _user.value = user
                    },
                    onFailure = { e ->
                        _errorMessage.value = e.message
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = "Registration failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = KtorClient.login(username, password)
                response.fold(
                    onSuccess = { loginResponse ->
                        _user.value = User(
                            id = loginResponse.id,
                            username = loginResponse.username,
                            name = loginResponse.name,
                            specialty = null // /login does not provide specialty
                        )
                    },
                    onFailure = { e ->
                        _errorMessage.value = e.message
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = "Login failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        _user.value = null
        _errorMessage.value = null
    }
}