package com.example.boletimintegral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.boletimintegral.databinding.FragmentUserScreenBinding
import com.example.boletimintegral.model.ContactFirebase
import com.example.boletimintegral.model.DataStudent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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




        val toolbar = (activity as ControlLoginActivity).findViewById<Toolbar>(R.id.toolbar)

        
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








