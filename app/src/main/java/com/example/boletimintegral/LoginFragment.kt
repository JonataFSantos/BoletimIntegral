package com.example.boletimintegral

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.boletimintegral.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding


    private val context: Activity by lazy {
        requireActivity()
    }

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    val toolbar = (activity as ControlLoginActivity).findViewById<Toolbar>(R.id.toolbar)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        goToUserScreen()

    }

    override fun onResume() {
        super.onResume()



        binding.btnToEnter.setOnClickListener {


            goToUserScreen()
            CoroutineScope(Dispatchers.Main).launch {
                try{
                    signUserAccount()
                }catch (e: FirebaseAuthInvalidCredentialsException) {
                    binding.edtName.error = "E-mail ou senha estão incorretos."
                }

            }

        }


        binding.btnCadaster.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_cadasterFragment)
        }
    }


    private fun goToUserScreen() {

        val userIsNotNull = auth.currentUser != null
        Log.i("appFlow", "Login of user is $userIsNotNull")

        if (userIsNotNull) {

            findNavController().navigate(
                R.id.action_loginFragment_to_userScreenFragment,
                navOptions = navOptions {
                    popUpTo(R.id.loginFragment){
                        inclusive = true
                    }
                }
            )




        }
    }

    private suspend fun signUserAccount() {

        val email = binding.edtName
        val password = binding.edtPassword

        if (isEdtTextNotNull(email)&& isEdtTextNotNull(password)){
            val emailValidated = email.text.toString()
            val passwordValidated = password.text.toString()

            var loginInSuccess = false

            auth.signInWithEmailAndPassword(
                emailValidated, passwordValidated
            )
                .addOnSuccessListener {
                    loginInSuccess  = true
                }
                .addOnFailureListener {
                    error ->
                    Log.e("appFlow",error.toString())
                }
                .await()

            if (loginInSuccess){

                findNavController().navigate(
                    R.id.action_loginFragment_to_userScreenFragment,
                    null,
                    navOptions = navOptions {
                        popUpTo(R.id.navigath_login){
                            inclusive = true
                        }
                    }
                )
            }
        }


    }

    private fun isEdtTextNotNull(edtText: TextView): Boolean {

        return if (edtText.text.isEmpty()) {
            edtText.error = "Preencha este campo"
            false
        } else {
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()


        toolbar.visibility = View.VISIBLE

    }

}



