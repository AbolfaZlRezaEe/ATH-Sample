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
import com.abproject.athsample.databinding.FragmentSignInBinding
import com.abproject.athsample.view.auth.AuthViewModel
import com.abproject.athsample.view.splash.SplashActivity
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class SingInFragment : ATHFragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignIn()
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressCallBack)
        binding.backButtonSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_singInFragment_to_welcomeFragment)
        }
    }

    private fun validationEditTexts(): Boolean {
        return binding.usernameSignInEditText.text.isNotEmpty()
                && binding.passwordSignInEditText.text.isNotEmpty()
    }

    private fun setupSignIn() {
        binding.loginButton.setOnClickListener {
            if (validationEditTexts()) {
                authViewModel.checkUserInformation(
                    username = binding.usernameSignInEditText.text.toString(),
                    password = binding.passwordSignInEditText.text.toString(),
                    requireContext()
                )
                authViewModel.checkUserInformationResult.observe(viewLifecycleOwner) {
                    if (it) {
                        startActivity(Intent(requireActivity(), SplashActivity::class.java))
                        requireActivity().finish()
                    } else
                        showSnackBar("Please enter valid Username or Password!")
                }
            } else
                showErrorInAuthEditTexts(
                    username = binding.usernameSignInEditText,
                    password = binding.passwordSignInEditText
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