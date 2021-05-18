package com.abproject.athsample.view.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.abproject.athsample.base.ATHFragment
import com.abproject.athsample.R
import com.abproject.athsample.databinding.FragmentSignUpBinding
import com.abproject.athsample.util.DateConverter
import com.abproject.athsample.util.checkEmailIsValid
import com.abproject.athsample.util.validationIranianPhoneNumber
import com.abproject.athsample.view.auth.AuthViewModel
import com.abproject.athsample.view.splash.SplashActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Abolfazl on 5/15/21
 */
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

    private fun validationEditTexts(): Boolean {
        return binding.usernameSignUpEditText.text.isNotEmpty()
                && binding.emailSignUpEditText.text.checkEmailIsValid()
                && binding.passwordSignUpEditText.text.isNotEmpty()
                && binding.firstNameSignUpEditText.text.isNotEmpty()
                && binding.lastNameSignUpEditText.text.isNotEmpty()
                && validationIranianPhoneNumber(binding.phoneNumberSignUpEditText.text.toString())
    }

    private fun setupSignUp() {
        binding.signUpButton.setOnClickListener {
            if (validationEditTexts()) {
                authViewModel.saveUserInformation(
                    binding.firstNameSignUpEditText.text.toString(),
                    binding.lastNameSignUpEditText.text.toString(),
                    binding.emailSignUpEditText.text.toString(),
                    binding.usernameSignUpEditText.text.toString(),
                    binding.phoneNumberSignUpEditText.text.toString(),
                    binding.passwordSignUpEditText.text.toString(),
                    DateConverter.convertDateToString(DateConverter.provideDate())
                )
                authViewModel.saveUserInformationStatus.observe(viewLifecycleOwner) {
                    if (it) {
                        startActivity(Intent(requireActivity(), SplashActivity::class.java))
                        requireActivity().finish()
                    } else {
                        showSnackBar("This Username hsa already been created!")
                    }
                }
            } else
                showErrorInAuthEditTexts(
                    binding.firstNameSignUpEditText,
                    binding.lastNameSignUpEditText,
                    binding.emailSignUpEditText,
                    binding.phoneNumberSignUpEditText,
                    binding.usernameSignUpEditText,
                    binding.passwordSignUpEditText
                )
        }
    }

    private val onBackPressCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}