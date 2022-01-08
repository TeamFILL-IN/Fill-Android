package com.teamfillin.fillin.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamfillin.fillin.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}