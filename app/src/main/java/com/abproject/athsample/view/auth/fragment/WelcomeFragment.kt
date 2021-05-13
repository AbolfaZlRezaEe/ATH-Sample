package com.abproject.athsample.view.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.abp.noties.base.ATHFragment
import com.abproject.athsample.R
import com.abproject.athsample.databinding.FragmentWelcomeBinding

class WelcomeFragment : ATHFragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInWelcomeButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_singInFragment)
        }
        binding.signUpWelcomeButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_singUpFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}