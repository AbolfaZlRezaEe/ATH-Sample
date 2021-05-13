package com.abproject.athsample.view.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.abp.noties.base.ATHFragment
import com.abproject.athsample.R
import com.abproject.athsample.databinding.FragmentSignUpBinding
import com.abproject.athsample.view.splash.SplashActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SingUpFragment : ATHFragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignUp()
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressCallBack)
        binding.backButtonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_singUpFragment_to_welcomeFragment)
        }
    }

    private fun validationEditText(): Boolean {
        return binding.emailSignUpEditText.text.isNotEmpty()
                && binding.usernameSignUpEditText.text.isNotEmpty()
                && binding.passwordSignUpEditText.text.isNotEmpty()
    }

    private fun setupSignUp() {
        binding.signUpButton.setOnClickListener {
            if (validationEditText()) {
                authViewModel.saveUserInformation(
                    binding.emailSignUpEditText.text.toString(),
                    binding.usernameSignUpEditText.text.toString(),
                    binding.passwordSignUpEditText.text.toString()
                )
                startActivity(Intent(requireActivity(), SplashActivity::class.java))
            } else
                showErrorInAuthEditTexts(
                    binding.emailSignUpEditText,
                    binding.usernameSignUpEditText,
                    binding.passwordSignUpEditText
                )
        }
    }

    val onBackPressCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}