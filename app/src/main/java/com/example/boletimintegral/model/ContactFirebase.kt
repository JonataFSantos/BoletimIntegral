package com.example.boletimintegral.model

import android.util.Log
import android.widget.TextView
import com.example.boletimintegral.databinding.FragmentLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await

class ContactFirebase {


    private var auth = Firebase.auth




    suspend fun getDataPerson(): User? {

        val db = Firebase.firestore

        val keyPerson = CoroutineScope(Dispatchers.IO).async {
            auth.currentUser!!.email.toString()
        }.await()


        var userPerson: User? = User()

        db.collection("users").document(keyPerson)
            .get()
            .addOnSuccessListener { document ->
                    userPerson = document.toObject<User>()
                    Log.i("appFlow", userPerson.toString())
                }
            .addOnFailureListener { exception ->
                    Log.w("appFlow", "Error getting documents.", exception)
                }.await()

        return userPerson

        }




    suspend fun getUserInList(email : String): User? {

        val db = Firebase.firestore


        Log.e("appFlow",email)

        var userPerson: User? = User()

        db.collection("users").document(email)
            .get()
            .addOnSuccessListener { document ->
                userPerson = document.toObject<User>()
                Log.i("appFlow", userPerson.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("appFlow", "Error getting documents.", exception)
            }.await()

        if (userPerson!=null){
            Log.e("appFlow",userPerson.toString())
        }else{
            Log.e("appFlow","resul -> is null")
        }

        return userPerson

    }









}



