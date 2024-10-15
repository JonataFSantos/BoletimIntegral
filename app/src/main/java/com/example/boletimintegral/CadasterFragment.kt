package com.example.boletimintegral

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.boletimintegral.databinding.FragmentCadasterBinding
import com.example.boletimintegral.model.User
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class CadasterFragment : Fragment(R.layout.fragment_cadaster) {

    private lateinit var binding: FragmentCadasterBinding

    private val auth by lazy {
        Firebase.auth
    }

    private val fireStore by lazy{
        Firebase.firestore
    }

    val toolbar = (activity as ControlLoginActivity).findViewById<Toolbar>(R.id.toolbar)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCadasterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.visibility = View.GONE



        binding.btnSendCadaster.setOnClickListener {

            Log.e("appFlow",areAllEditTextsFilled().toString())

            if (areAllEditTextsFilled() && isPasswordValid()) {

                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        createCount()
                }catch (e: FirebaseAuthUserCollisionException){
                    binding.edtCadasterEmail.error = "Este email já existe."
                }catch (e: FirebaseAuthInvalidCredentialsException){
                    binding.edtCadasterEmail.error = "Este e-mail não é válido. " +
                            "Por favor, verifique e tente novamente."

                    }
                }
            }


        }
    }

    private fun isEdtTextNotNull(edtText: TextView) : Boolean{

       return if (edtText.text.isEmpty()){
           edtText.error = "Preencha este campo"
           false
        }else{
            true
        }

    }

    private fun emailAndPassword ():  Pair<String,String> = Pair(
         binding.edtCadasterEmail.text.toString(),
         binding.edtCadasterPassword.text.toString()
     )

    private fun areAllEditTextsFilled() : Boolean {
        return (isEdtTextNotNull(binding.edtCadasterEmail)
                &&isEdtTextNotNull(binding.edtCadasterPassword)
                &&isEdtTextNotNull(binding.edtCadasterName)
                && isEdtTextNotNull(binding.edtCadasterLastName)
                && isEdtTextNotNull(binding.edtPhone)
                )

    }







    private fun isPasswordValid() : Boolean =
        if (binding.edtCadasterPassword.text.toString().length >= 6){
            true
        } else{
            binding.edtCadasterPassword.error = "Senha precisa ter mais de 6 digitos"
            false
        }






    private fun dataUser() : User = User(
        email = binding.edtCadasterEmail.text.toString(),
        name = binding.edtCadasterName.text.toString(),
        lastName = binding.edtCadasterLastName.text.toString(),
        phone = binding.edtPhone.text.toString()
    )


    private suspend fun createCount() {

        var createCountSuccess = false

        auth.createUserWithEmailAndPassword(
            emailAndPassword().first, emailAndPassword().second
            )
            .addOnSuccessListener {
                    storeUserDetails()
                createCountSuccess = true
            }
            .addOnFailureListener {
                error->
                Log.e("appFlow", error.toString())
            }.await()

        if (createCountSuccess){
            findNavController().navigate(
                R.id.action_loginFragment_to_userScreenFragment
            )
        }
    }


    private fun storeUserDetails(){

        CoroutineScope(Dispatchers.IO).launch {
            fireStore.collection("users").document(dataUser().email)
                .set(dataUser())
                .await()
        }

    }


    override fun onDestroy() {
        super.onDestroy()


        toolbar.visibility = View.VISIBLE

    }
}



