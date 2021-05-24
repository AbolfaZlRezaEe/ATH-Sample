package com.abproject.athsample.view.splash

import android.app.Application
import com.abproject.athsample.base.ATHViewModel
import com.abproject.athsample.data.repository.MainRepository

/**
 * Created by Abolfazl on 5/15/21
 */
class SplashViewModel(
    private val app: Application,
    private val mainRepository: MainRepository
) : ATHViewModel(app) {

    fun checkExistingUser(): Boolean {
        return mainRepository.checkExistingUserInSharedPrefs()
    }
}