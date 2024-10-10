package com.example.boletimintegral

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.boletimintegral.databinding.FragmentMessageBinding
import com.example.boletimintegral.model.ContactFirebase
import com.example.boletimintegral.model.Message
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MessageFragment : Fragment(R.layout.fragment_message) {

    private lateinit var binding: FragmentMessageBinding

    private val db by lazy {
        Firebase.firestore
    }

    var message = mutableListOf<Message>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        CoroutineScope(Dispatchers.Main).launch {
            val result = db.collection("message")
                .get().await()

                    for (document in result) {

                        val dataMessage: Message = document.toObject<Message>()
                        checkedAccessMessage(updateMessage = dataMessage)

                    }




            delay(100)
            Log.i("appFlow", "Tempo")
            updateMessage(message)
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            userIsCoordinator()
        }


        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_messageFragment_to_messagemSendFragment)

        }


    }





    private suspend fun checkedAccessMessage(updateMessage: Message) {

        val dataPerson = CoroutineScope(Dispatchers.Main).async {
            ContactFirebase().getDataPerson()!!.accessMessage
        }.await()

        val leonardoMorningCheck =
            dataPerson.leonardoMorning && updateMessage.accessMessage.leonardoMorning

        val leonardoAfternoonCheck =
            dataPerson.leonardoAfternoon && updateMessage.accessMessage.leonardoAfternoon

        val bernalMorningCheck =
            dataPerson.bernalMorning && updateMessage.accessMessage.bernalMorning

        val bernalAfternoonCheck =
            dataPerson.bernalAfternoon && updateMessage.accessMessage.bernalAfternoon


        Log.e("appFlow","""${updateMessage.titleMessage} 
            |$leonardoAfternoonCheck ||
            |$leonardoAfternoonCheck ||
            |$bernalMorningCheck ||
            |$bernalAfternoonCheck
        """.trimMargin())


         if (leonardoMorningCheck||
        leonardoAfternoonCheck||
        bernalMorningCheck||
        bernalAfternoonCheck){
            message.add(updateMessage)
             Log.e("appFlow","${updateMessage.titleMessage} adicionado")
        }

    }

    private fun updateMessage(message: MutableList<Message>) {

        Log.e("appflow","${message.toString()} é mensagem")
        binding.recyclerMessage.adapter = MessageAdapter(message)
    }

    private suspend fun userIsCoordinator(){

        val dataPerson = CoroutineScope(Dispatchers.Main).async {
            ContactFirebase().getDataPerson()!!
        }.await()


        if(dataPerson.accessControl == "Coordenador"){
                binding.floatingActionButton.visibility = View.VISIBLE
            }
    }

}