package com.example.boletimintegral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.boletimintegral.databinding.FragmentAccessBinding
import com.example.boletimintegral.databinding.FragmentUserProfileScreenBinding


class UserProfileScreenFragment : Fragment(R.layout.fragment_user_profile_screen) {

    lateinit var binding : FragmentUserProfileScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileScreenBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}