package com.example.heartdiseasepredictionandroid.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.heartdiseasepredictionandroid.R
import com.example.heartdiseasepredictionandroid.viewmodel.AuthViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText: TextInputEditText = findViewById(R.id.et_username)
        val passwordEditText: TextInputEditText = findViewById(R.id.et_password)
        val loginButton: MaterialButton = findViewById(R.id.btn_login)

        authViewModel.loginResult.observe(this) { result ->
            if (result.isSuccess) {
                val user = authViewModel.user.value
                val intent = Intent(this, LandingActivity::class.java) // Changed to LandingActivity
                intent.putExtra("USER_ID", user?.id?.toString())
                intent.putExtra("USERNAME", user?.username)
                intent.putExtra("NAME", user?.name ?: "Doctor")
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, result.exceptionOrNull()?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(username, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}