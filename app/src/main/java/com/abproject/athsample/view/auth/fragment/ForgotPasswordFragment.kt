package com.abproject.athsample.view.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.abproject.athsample.R
import com.abproject.athsample.base.ATHFragment
import com.abproject.athsample.databinding.FragmentForgotPasswordInBinding
import com.abproject.athsample.util.Resource
import com.abproject.athsample.util.Variables.EXTRA_KEY_CODE
import com.abproject.athsample.util.Variables.EXTRA_KEY_EMAIL
import com.abproject.athsample.util.checkEmailIsValid
import com.abproject.athsample.view.auth.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Abolfazl on 5/20/21
 */
class ForgotPasswordFragment : ATHFragment() {

    private var _binding: FragmentForgotPasswordInBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressCallBack)
        setupForgotPasswordButton()
        binding.backButtonSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_singInFragment)
        }
    }

    private fun setupForgotPasswordButton() {
        binding.sendCodeButton.setOnClickListener {
            if (validationEmailEditText()) {
                authViewModel.sendEmailToUser(
                    binding.emailForgotPasswordEditText.text.toString()
                )?.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resource.Loading -> {
                            showProgressBar(true)
                        }
                        is Resource.Success -> {
                            showProgressBar(false)
                            response.data?.let { code ->
                                if (code.isNotEmpty()) {
                                    setupNavigationToResetPassword(
                                        binding.emailForgotPasswordEditText.text.toString(),
                                        code
                                    )
                                }
                            }
                        }
                        is Resource.Error -> {
                            showProgressBar(false)
                            response.message?.let { message ->
                                showSnackBar(message, Snackbar.LENGTH_LONG)
                            }
                        }
                    }
                } ?: showSnackBar("No Internet connection. please try again!")
            } else
                setErrorForEditText()
        }
    }

    private fun setErrorForEditText() {
        if (binding.emailForgotPasswordEditText.text.isEmpty())
            binding.emailForgotPasswordEditText.error = getString(R.string.emailError)
        if (!binding.emailForgotPasswordEditText.text.checkEmailIsValid())
            binding.emailForgotPasswordEditText.error = getString(R.string.wrongEmail)
    }

    private fun setupNavigationToResetPassword(email: String, code: String) {
        val bundle = bundleOf(
            EXTRA_KEY_EMAIL to email,
            EXTRA_KEY_CODE to code
        )
        findNavController().navigate(
            R.id.action_forgotPasswordFragment_to_resetPasswordFragment,
            bundle
        )
    }

    private fun validationEmailEditText(): Boolean {
        return binding.emailForgotPasswordEditText.text.isNotEmpty()
                && binding.emailForgotPasswordEditText.text.checkEmailIsValid()
    }

    private val onBackPressCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }
}