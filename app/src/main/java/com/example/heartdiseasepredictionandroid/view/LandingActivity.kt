package com.example.heartdiseasepredictionandroid.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.heartdiseasepredictionandroid.R
import com.google.android.material.button.MaterialButton

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val userId = intent.getStringExtra("USER_ID") ?: return
        val username = intent.getStringExtra("USERNAME") ?: return
        val name = intent.getStringExtra("NAME") ?: "Doctor"

        val viewRecordsButton: MaterialButton = findViewById(R.id.btn_view_records)
        val diagnoseButton: MaterialButton = findViewById(R.id.btn_diagnose)

        viewRecordsButton.setOnClickListener {
            val intent = Intent(this, RecordsActivity::class.java)
            intent.putExtra("USER_ID", userId)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        diagnoseButton.setOnClickListener {
            val intent = Intent(this, ModelSelectionActivity::class.java)
            intent.putExtra("USER_ID", userId)
            intent.putExtra("USERNAME", username)
            intent.putExtra("NAME", name)
            startActivity(intent)
        }
    }
}