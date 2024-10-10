package com.example.boletimintegral

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.boletimintegral.databinding.FragmentAccessSelectedBinding
import com.example.boletimintegral.model.ContactFirebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AccessSelectedFragment : Fragment() {

    private lateinit var binding : FragmentAccessSelectedBinding

    private val args : AccessSelectedFragmentArgs by navArgs()

    private val firestore by lazy {
        Firebase.firestore
    }

    private val user by lazy {
        args.user
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccessSelectedBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        accessControlToast()

        if (args.user.accessMessage.leonardoMorning){
            binding.accessLeonardoMorning.isChecked=true
        }
        if (args.user.accessMessage.leonardoAfternoon){
            binding.accessLeonardoAfternoon.isChecked=true
        }
        if (args.user.accessMessage.bernalMorning){
            binding.accessBernalMorning.isChecked=true
        }
        if (args.user.accessMessage.bernalAfternoon){
            binding.accessBernalAfternoon.isChecked=true
        }


        CoroutineScope(Dispatchers.Main).launch {

            val accessControl = async { ContactFirebase().getDataPerson()!!.accessControl }.await()

            accessControlOfTeacher(accessControl)
            accessControlOfCoordinator(accessControl)

        }








        binding.button2.setOnClickListener {

            updateAccessControl()
            upDateAccessMessage()

            Log.i("appFlow",user.accessControl)

                firestore.collection("users")
                    .document(user.email)
                    .set(user)

            findNavController().popBackStack()

        }


    }

    private fun accessControlToast() {

        if (user.accessControl == "Bloqueado"){
            binding.chipIsBlocked.isChecked=true
        }else if(user.accessControl == "Usuário"){
            binding.chipUserIsUser.isChecked=true
        }else if(user.accessControl == "Professor") {
            binding.chipUserTeacher.isChecked=true
        } else if (user.accessControl == "Coordenador"){
            binding.chipUserCoordinator.isChecked=true

        }
    }


    private fun updateAccessControl() {

        if (binding.chipIsBlocked.isChecked){
            user.accessControl = "Bloqueado"
        }else if(binding.chipUserIsUser.isChecked){
            user.accessControl = "Usuário"
        }else if(binding.chipUserTeacher.isChecked) {
            user.accessControl = "Professor"
        } else if (binding.chipUserCoordinator.isChecked){
            user.accessControl = "Coordenador"
        }
    }


    private fun upDateAccessMessage()   {
        user.accessMessage.leonardoMorning = binding.accessLeonardoMorning.isChecked
        user.accessMessage.leonardoAfternoon = binding.accessLeonardoAfternoon.isChecked
        user.accessMessage.bernalMorning = binding.accessBernalMorning.isChecked
        user.accessMessage.bernalAfternoon = binding.accessBernalAfternoon.isChecked
    }








    private fun accessControlOfCoordinator(accessControl : String){
        if (accessControl=="Coordenador"){
            binding.tvTitleControlSchool.visibility = View.VISIBLE
            binding.chipIsBlocked.visibility = View.VISIBLE
            binding.chipUserIsUser.visibility = View.VISIBLE
            binding.chipUserTeacher.visibility = View.VISIBLE
            binding.chipUserCoordinator.visibility = View.VISIBLE
            binding.viewLine2.visibility = View.VISIBLE
        }
    }

    private fun accessControlOfTeacher(accessControl : String){
        if (accessControl=="Professor"){
            binding.tvTitleControlSchool.visibility = View.VISIBLE
            binding.chipIsBlocked.visibility = View.VISIBLE
            binding.chipUserIsUser.visibility = View.VISIBLE
            binding.viewLine2.visibility = View.VISIBLE
        }
    }





}