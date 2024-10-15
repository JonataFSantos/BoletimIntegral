package com.example.boletimintegral

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.boletimintegral.databinding.ActivityControlLoginBinding

class ControlLoginActivity : AppCompatActivity() {

    private val binding : ActivityControlLoginBinding by lazy {
        ActivityControlLoginBinding.inflate(layoutInflater)
    }

    private  lateinit var navController : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val navHostFragment = (supportFragmentManager.findFragmentById(
            binding.fragmentContainerView.id
        )
                ) as NavHostFragment

        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController,appBarConfiguration)




    }
}
