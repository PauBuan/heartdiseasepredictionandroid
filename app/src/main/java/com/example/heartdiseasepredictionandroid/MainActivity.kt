package com.example.heartdiseasepredictionandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.heartdiseasepredictionandroid.view.LoginActivity
import com.example.heartdiseasepredictionandroid.view.RegisterActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton: MaterialButton = findViewById(R.id.btn_login)
        val registerButton: MaterialButton = findViewById(R.id.btn_register)

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}