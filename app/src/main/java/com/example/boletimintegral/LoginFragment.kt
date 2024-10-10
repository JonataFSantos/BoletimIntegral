package com.example.boletimintegral

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.boletimintegral.databinding.FragmentLoginBinding
import com.example.boletimintegral.model.ContactFirebase
import com.example.boletimintegral.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
            findNavController().navigate(R.id.action_loginFragment_to_userActivity)
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
                findNavController().navigate(R.id.action_loginFragment_to_userActivity)
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

}



