package com.example.boletimintegral.api

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.boletimintegral.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class ConnectFirebaseEat(private val binding: FragmentLoginBinding, private val context : Activity) {

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }
    var email : String = binding.edtName.text.toString()
    var password : String = binding.edtPassword.text.toString()


    fun createUserLogin( ) {


         var toWarnUser  = "Login inválido"

         val emailOrPasswordNotIsNull : Boolean = email=="" || password == ""

         if (emailOrPasswordNotIsNull){
             toWarnUser = "Email ou senha estão vazíos"
         }

        auth.createUserWithEmailAndPassword(email,password)

            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("appFlow", "createUserWithEmail:success")

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("appFlow", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        toWarnUser,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    fun signUserAccount() : String{

        var result = ""

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                task->
                result = "successful registration"
            }
            .addOnFailureListener {
                result = "registration unsuccessful"
            }

        return result

    }

}