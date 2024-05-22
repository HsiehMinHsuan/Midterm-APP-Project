package com.example.assignment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.assignment.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val start=findViewById<Button>(R.id.btn_st)
        start.setOnClickListener {
            val intent = Intent(this, SecActivity::class.java)
            startActivity(intent)
        }
    }
}

