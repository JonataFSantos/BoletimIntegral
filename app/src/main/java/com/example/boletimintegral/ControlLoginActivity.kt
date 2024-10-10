package com.example.boletimintegral

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.boletimintegral.databinding.ActivityControlLoginBinding

class ControlLoginActivity : AppCompatActivity() {

    private val binding : ActivityControlLoginBinding by lazy {
        ActivityControlLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



    }
}
