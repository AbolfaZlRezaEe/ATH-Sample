package com.abp.noties.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.abp.noties.R
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

    fun showEmptyState(layoutResId: Int = R.layout.empty_state, show: Boolean = false): View? {
        rootView?.let { coordinatorLayout ->
            viewContext?.let { context ->
                var emptyState =
                    coordinatorLayout.findViewById<View>(R.id.homeEmptyStateRootView)
                if (emptyState == null) {
                    emptyState =
                        LayoutInflater.from(context)
                            .inflate(layoutResId, coordinatorLayout, false)
                    coordinatorLayout.addView(emptyState)
                }
                if (show)
                    emptyState.visibility = View.VISIBLE
                else
                    emptyState.visibility = View.GONE
                return emptyState
            }
        }
        return null
    }

    fun showErrorInAuthEditTexts(
        email: EditText? = null,
        username: EditText,
        password: EditText
    ) {
        if (email != null) {
            email.error = viewContext?.getString(R.string.emailError)
            username.error = viewContext?.getString(R.string.usernameError)
            password.error = viewContext?.getString(R.string.passwordError)
        } else {
            username.error = viewContext?.getString(R.string.usernameError)
            password.error = viewContext?.getString(R.string.passwordError)
        }
    }
}
