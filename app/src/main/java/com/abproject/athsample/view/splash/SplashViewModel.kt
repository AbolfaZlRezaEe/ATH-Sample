package com.abproject.athsample.view.splash

import com.abproject.athsample.base.ATHViewModel
import com.abproject.athsample.data.repository.MainRepository

/**
 * Created by Abolfazl on 5/15/21
 */
class SplashViewModel(
    private val mainRepository: MainRepository
) : ATHViewModel() {

    fun checkExistingUser(): Boolean {
        return mainRepository.checkExistingUserInSharedPrefs()
    }
}