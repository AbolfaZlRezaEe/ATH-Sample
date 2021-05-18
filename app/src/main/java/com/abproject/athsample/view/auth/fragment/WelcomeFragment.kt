package com.abproject.athsample.view.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.abproject.athsample.base.ATHFragment
import com.abproject.athsample.R
import com.abproject.athsample.databinding.FragmentWelcomeBinding

/**
 * Created by Abolfazl on 5/15/21
 */
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
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressCallBack)
        binding.signInWelcomeButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_singInFragment)
        }
        binding.signUpWelcomeButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_singUpFragment)
        }
    }

    val onBackPressCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}