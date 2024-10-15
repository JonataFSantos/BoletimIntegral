package com.example.boletimintegral

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
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

        binding.toolbar

        val navHostFragment = (supportFragmentManager.findFragmentById(
            binding.fragmentContainerView.id
        )
                ) as NavHostFragment



        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        // instancia a função permite o meu navControler controlar
        // a boolbar da minha activity.

        setSupportActionBar(binding.toolbar)


        binding.toolbar.setupWithNavController(navController, appBarConfiguration)


        navController.addOnDestinationChangedListener{_,destination,_->

            when (destination.id) {
                R.id.loginFragment -> {
                    binding.toolbar.visibility = View.GONE // Esconde a Toolbar
                }
                R.id.userScreenFragment -> {
                    binding.toolbar.visibility = View.VISIBLE // Esconde a Toolbar
                }

                }
            }






    }
}
