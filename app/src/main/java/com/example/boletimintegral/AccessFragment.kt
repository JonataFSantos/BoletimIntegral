package com.example.boletimintegral

import android.app.DirectAction
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.boletimintegral.databinding.FragmentAccessBinding
import com.example.boletimintegral.model.Message
import com.example.boletimintegral.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AccessFragment : Fragment() {

    private lateinit var _binding: FragmentAccessBinding

    private val fireStore by lazy {
        Firebase.firestore
    }
    private val context by lazy{
        requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccessBinding.inflate(inflater, container, false)
        return _binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val users = mutableListOf<User>()


        CoroutineScope(Dispatchers.Main).launch {
            fireStore.collection("users")
                .get()
                .addOnSuccessListener { result ->

                    for (document in result) {

                        val dataUser: User = document.toObject<User>()

                        users.add(dataUser)
                        Log.i("appFlow", "$dataUser")
                    }
                    Log.i("appFlow", "Tempo")

                }.await()
            delay(100)
            updateUsersControl(users)

        }

    }

    private fun updateUsersControl(user: MutableList<User>) {
        val accessAdapter = AccessAdapter(user,context,_binding){user->

            val action =
                AccessFragmentDirections
                    .actionAccessFragmentToAccessSelectedFragment3(
                    user
                )

            findNavController().navigate(action)
        }
        _binding.recyclerAcess.adapter = accessAdapter

    }


}