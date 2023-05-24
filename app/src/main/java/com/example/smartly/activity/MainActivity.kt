package com.example.smartly.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartly.R
import com.example.smartly.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}