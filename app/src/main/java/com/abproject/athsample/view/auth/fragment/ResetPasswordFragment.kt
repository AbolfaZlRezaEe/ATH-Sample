package com.abproject.athsample.view.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.abproject.athsample.R
import com.abproject.athsample.base.ATHFragment
import com.abproject.athsample.databinding.FragmentResetPasswordBinding
import com.abproject.athsample.util.Resource
import com.abproject.athsample.util.Variables.EXTRA_KEY_CODE
import com.abproject.athsample.util.Variables.EXTRA_KEY_EMAIL
import com.abproject.athsample.view.auth.AuthViewModel
import com.abproject.athsample.view.splash.SplashActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Abolfazl on 5/20/21
 */
class ResetPasswordFragment : ATHFragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressCallBack)
        showSnackBar("Email sent. Please check your Email...", Snackbar.LENGTH_LONG)
        setupForgotPasswordButton()
        binding.backButtonSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_singInFragment)
        }
    }

    private fun setupForgotPasswordButton() {
        binding.loginResetPasswordButton.setOnClickListener {
            if (validationEditTexts()) {
                val email: String = arguments?.getString(EXTRA_KEY_EMAIL) ?: ""
                authViewModel.changeUserPasswordAndUpdate(
                    email,
                    binding.passwordResetPasswordEditText.text.toString()
                )
                authViewModel.resetPasswordStatus.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resource.Loading -> {
                            showProgressBar(true)
                        }
                        is Resource.Success -> {
                            showProgressBar(false)
                            response.data?.let {
                                if (it)
                                    showAlertDialog()
                            }
                        }
                        is Resource.Error -> {
                            showProgressBar(false)
                            response.message?.let { message ->
                                showSnackBar(message, Snackbar.LENGTH_LONG)
                            }
                        }
                    }
                }
            } else
                setErrorForEditText()
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.resetPassword))
            .setMessage(getString(R.string.resetPasswordIsSuccess))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                startActivity(Intent(requireActivity(), SplashActivity::class.java))
                dialog.dismiss()
                requireActivity().finish()
            }
            .show()
    }

    private fun setErrorForEditText() {
        val code: String = arguments?.getString(EXTRA_KEY_CODE) ?: ""
        if (binding.codeResetPasswordEditText.text.isEmpty())
            binding.codeResetPasswordEditText.error = getString(R.string.codeError)
        if (binding.codeResetPasswordEditText.text.toString() == code)
            showSnackBar(getString(R.string.wrongCode))
        if (binding.passwordResetPasswordEditText.text.isEmpty())
            binding.passwordResetPasswordEditText.error = getString(R.string.passwordError)
    }

    private fun validationEditTexts(): Boolean {
        val code: String = arguments?.getString(EXTRA_KEY_CODE) ?: ""
        return binding.codeResetPasswordEditText.text.isNotEmpty()
                && binding.passwordResetPasswordEditText.text.isNotEmpty()
                && binding.codeResetPasswordEditText.text.toString() == code
    }

    private val onBackPressCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }
}