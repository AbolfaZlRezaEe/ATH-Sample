package com.abproject.athsample.base

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.abproject.athsample.R
import com.abproject.athsample.util.checkEmailIsValid
import com.abproject.athsample.util.phoneNumberIsValid
import com.google.android.material.snackbar.Snackbar

interface ATHInterface {
    val rootView: CoordinatorLayout?
    val viewContext: Context?
    fun showProgressBar(isShow: Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingState = it.findViewById<View>(R.id.loadingState)
                if (loadingState == null && isShow) {
                    loadingState = LayoutInflater.from(context)
                        .inflate(R.layout.state_loading, rootView, false)
                    it.addView(loadingState)
                }
                loadingState?.visibility = if (isShow) View.VISIBLE else View.GONE
            }
        }
    }

    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        rootView?.let {
            Snackbar.make(it, message, duration).show()
        }
    }


    fun showErrorInAuthEditTexts(
        firstName: EditText? = null,
        lastName: EditText? = null,
        email: EditText? = null,
        phoneNumber: EditText? = null,
        username: EditText,
        password: EditText
    ) {
        if (email != null
            && firstName != null
            && lastName != null
            && phoneNumber != null
        ) {
            if (firstName.text.isEmpty())
                firstName.error = viewContext?.getString(R.string.firstNameError)
            if (lastName.text.isEmpty())
                lastName.error = viewContext?.getString(R.string.lastNameError)
            if (email.text.isEmpty() || !email.text.checkEmailIsValid())
                email.error = viewContext?.getString(R.string.emailError)
            if (username.text.isEmpty())
                username.error = viewContext?.getString(R.string.usernameError)
            if (password.text.isEmpty())
                password.error = viewContext?.getString(R.string.passwordError)
            if (phoneNumber.text.isEmpty() || !phoneNumberIsValid(phoneNumber.text.toString()))
                phoneNumber.error = viewContext?.getString(R.string.phoneNumberError)
        } else {
            if (username.text.isEmpty())
                username.error = viewContext?.getString(R.string.usernameError)
            if (password.text.isEmpty())
                password.error = viewContext?.getString(R.string.passwordError)
        }
    }
}
