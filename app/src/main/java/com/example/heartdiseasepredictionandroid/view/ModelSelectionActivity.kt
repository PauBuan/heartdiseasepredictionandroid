package com.example.heartdiseasepredictionandroid.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.heartdiseasepredictionandroid.R
import com.google.android.material.button.MaterialButton

class ModelSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_selection)

        val userId = intent.getStringExtra("USER_ID") ?: return
        val username = intent.getStringExtra("USERNAME") ?: return
        val name = intent.getStringExtra("NAME") ?: "Doctor"

        val clusterButton: MaterialButton = findViewById(R.id.btn_cluster)
        val neuralButton: MaterialButton = findViewById(R.id.btn_neural)

        clusterButton.setOnClickListener {
            val intent = Intent(this, DiagnoseActivity::class.java)
            intent.putExtra("USER_ID", userId)
            intent.putExtra("USERNAME", username)
            intent.putExtra("NAME", name)
            intent.putExtra("MODEL_TYPE", "Cluster")
            startActivity(intent)
        }

        neuralButton.setOnClickListener {
            val intent = Intent(this, DiagnoseActivity::class.java)
            intent.putExtra("USER_ID", userId)
            intent.putExtra("USERNAME", username)
            intent.putExtra("NAME", name)
            intent.putExtra("MODEL_TYPE", "Neural Network")
            startActivity(intent)
        }
    }
}