package com.example.boletimintegral

import android.content.ContentValues.TAG
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.boletimintegral.databinding.FragmentUserScreenBinding
import com.example.boletimintegral.model.ContactFirebase
import com.example.boletimintegral.model.DataStudent
import com.example.boletimintegral.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.job
import kotlinx.coroutines.launch


class UserScreenFragment : Fragment(R.layout.fragment_user_screen) {

    private lateinit var binding: FragmentUserScreenBinding


    private var getUSer : MutableList<DataStudent>  = mutableListOf<DataStudent>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserScreenBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        
        CoroutineScope(Dispatchers.Main).launch {

            val user = CoroutineScope(Dispatchers.Main).async {
                ContactFirebase().getDataPerson()
            }.await()

            val name = user!!.name

            binding.tvUser.text = getString(R.string.heloo, name)

            binding.tvEmail.text = user.email


                getUSer = user.childrensData

                val studentAdapter = StudentAdapter(getUSer)
                binding.recyclerView.adapter=studentAdapter


            }





        binding.imgViewAddChildren.setOnClickListener {
            findNavController().navigate(R.id.action_userScreenFragment_to_addChildrenFragment)
        }

        }


}








