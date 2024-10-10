package com.example.boletimintegral

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.boletimintegral.databinding.FragmentAddChildrenBinding
import com.example.boletimintegral.model.ContactFirebase
import com.example.boletimintegral.model.DataStudent
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AddChildrenFragment : Fragment(R.layout.fragment_add_children) {

    private lateinit var  binding : FragmentAddChildrenBinding

    private val db by lazy {
        Firebase.firestore
    }

    private val contactFirebase by lazy {
        ContactFirebase()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddChildrenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.button.setOnClickListener {

            setSchoolOfStudent()

            val fullName = binding.edtName.text.toString()

            CoroutineScope(Dispatchers.Main).async {

                val user = CoroutineScope (Dispatchers.Main).async {
                    contactFirebase.getDataPerson()
                }.await()


                val dataStudent = DataStudent(
                    fullName = fullName,
                    responsibleEmail = user!!.email,
                    responsibleName = "${user.name} ${user.lastName}",
                    school = setSchoolOfStudent(),
                    phone = user.phone
                    
                )

                user.childrensData.add(dataStudent)

                CoroutineScope(Dispatchers.IO).launch {
                    db.collection("users").document(user.email)
                        .set(user)
                }

            }

            findNavController().popBackStack()

        }

    }

    private fun setSchoolOfStudent() : String {
        val school = if(binding.chipSetSchoolLeonardoMorning.isChecked){
            "Leonardo - Manhã"
        }else if (binding.chipSetSchoolLeonardoAfternoon.isChecked){
            "Leonardo - Tarde"
        }else if(binding.chipSetSchoolBernalMorning.isChecked){
            "Bernal - Manhã"
        }else {
            "Bernal = Tarde"
        }

        return school


    }




}