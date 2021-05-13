package com.abproject.athsample.view.splash

import android.content.SharedPreferences
import com.abp.noties.base.ATHViewModel
import com.abproject.athsample.util.Variables.IS_EXISTING_KEY

class SplashViewModel(
    private val sharedPreferences: SharedPreferences
) : ATHViewModel() {

    fun checkExistingUser(): Boolean {
        return sharedPreferences.getBoolean(IS_EXISTING_KEY, false)
    }
}