package com.abproject.athsample.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.abproject.athsample.R
import com.abproject.athsample.util.checkEmailIsValid
import com.abproject.athsample.util.validationIranianPhoneNumber
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Abolfazl on 5/13/21
 */
interface ATHInterface {
    val rootView: CoordinatorLayout?
    val viewContext: Context?

    /**
     * showProgressBar Function use in views (Activity or Fragment)
     * and that help us for showing progressbar in view.
     * Note: this function only works on views that have Coordinator Layout!
     */
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

    /**
     * showSnackBar is a custom Function I made for views
     * this function working in Activities and Fragments.
     * Note: this Function only works on views that have Coordinator Layout!
     */
    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        rootView?.let {
            Snackbar.make(it, message, duration).show()
        }
    }

    /**
     * showErrorInAuthEditText function made for Auth-Section in the App.
     * this function takes multiple EditText form the input and then
     * displays predefined Errors on them.
     * so because this function call multiple time in Auth-Section,
     * I'm put it in ATHInterface.
     */
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
            if (phoneNumber.text.isEmpty() || !validationIranianPhoneNumber(phoneNumber.text.toString()))
                phoneNumber.error = viewContext?.getString(R.string.phoneNumberError)
        } else {
            if (username.text.isEmpty())
                username.error = viewContext?.getString(R.string.usernameError)
            if (password.text.isEmpty())
                password.error = viewContext?.getString(R.string.passwordError)
        }
    }
}
