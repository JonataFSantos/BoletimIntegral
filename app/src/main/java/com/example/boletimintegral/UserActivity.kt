
package com.example.boletimintegral

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.boletimintegral.databinding.ActivityUserBinding
import com.example.boletimintegral.model.ContactFirebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.installations.ktx.installations
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class UserActivity : AppCompatActivity() {


    private lateinit var _binding : ActivityUserBinding

    private val auth by lazy {
        Firebase.auth
    }

    private val firestore by lazy {
        Firebase.firestore
    }

    private val db by lazy {
        ContactFirebase()
    }

    private  lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        _binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(_binding.root)





      Firebase.installations.getToken(true)




        // Instancia o o naviHost que está no container view com xml da Activity da tela do usuário.
        val navHostFragment = (supportFragmentManager.findFragmentById(
            _binding.fragmentContainerView.id
        )
                ) as NavHostFragment



        navController = navHostFragment.navController


        // instancia o navControler

        val drawer = _binding.drawer


        val appBarConfiguration = AppBarConfiguration(navController.graph)


        // instancia a função permite o meu navControler controlar
        // a boolbar da minha activity.

        setSupportActionBar(_binding.toolbar)


        _binding.toolbar.setupWithNavController(navController, appBarConfiguration)





            // instancia a função permite o meu navControler controlar
            // a bottomBottomBar da minha activity.
        _binding.bottomAppBar.setupWithNavController(navController)

        CoroutineScope(Dispatchers.Main).launch {
            val accessController  = db.getDataPerson()!!.accessControl

            if (accessController=="Coordenador"||accessController=="Professor"){
                _binding.bottomAppBar.menu.findItem(R.id.accessFragment).isVisible = true
            }else{
                _binding.bottomAppBar.menu.findItem(R.id.accessFragment).isVisible = false
            }
        }






    }


    override fun onStart() {
        super.onStart()



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tool_bar, menu) // Menu global disponível em todas as telas
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {


                CoroutineScope(Dispatchers.Main).async{
                    auth.signOut()
                    delay(400)
                }


//                navController.navigate(R.id.action_userScreenFragment_to_navigath_login)
//
//                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}