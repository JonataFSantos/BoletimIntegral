package com.example.boletimintegral

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.boletimintegral.databinding.FragmentMessagemSendBinding
import com.example.boletimintegral.model.AccessMessage
import com.example.boletimintegral.model.Message
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MessagemSendFragment : Fragment(R.layout.fragment_messagem_send) {

    private lateinit var binding : FragmentMessagemSendBinding

    private val db by lazy {
        Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagemSendBinding.inflate(inflater,container,false)
        return binding.root
        }


    override fun onResume() {
        super.onResume()

        Log.i("appFlow","oi")



        binding.imgButtomSend.setOnClickListener {

            val upDateAccessMessage = updateAccessMessage()

            
            val titleMessage = binding.edtTitleMessage.text.toString()
            val messageDescription = binding.edtMessageDescription.text.toString()
            val message  = Message(
                titleMessage = titleMessage,
                message = messageDescription,
                accessMessage = upDateAccessMessage)




            CoroutineScope(Dispatchers.IO).launch {
                Log.i("appFlow","success")
                db.collection("message").document(message.id)
                    .set(message).await()
                delay(1000)
            }



            returnPopBackStack()





        }
    }

    private fun returnPopBackStack(){
        findNavController().popBackStack()
    }


    private fun updateAccessMessage() : AccessMessage =
        AccessMessage(
            leonardoMorning = binding.chipLeonardoMorning.isChecked,
            leonardoAfternoon = binding.chipLeonardoAfternoon.isChecked,
            bernalMorning = binding.chipBernalMorning.isChecked,
            bernalAfternoon = binding.chipBernalAfternoon.isChecked)




}