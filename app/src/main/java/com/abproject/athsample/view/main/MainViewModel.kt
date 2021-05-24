package com.abproject.athsample.view.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abproject.athsample.base.ATHViewModel
import com.abproject.athsample.data.dataclass.User
import com.abproject.athsample.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Abolfazl on 5/16/21
 */
class MainViewModel(
    private val app: Application,
    private val mainRepository: MainRepository
) : ATHViewModel(app) {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        fetchDataFromDb()
    }

    private fun fetchDataFromDb() = viewModelScope.launch {
        val response = mainRepository.getUsers()
        withContext(Dispatchers.Main) {
            _users.value = response
        }
    }

    fun clearDataFromStorage() {
        mainRepository.clearDataFromSharedPrefs()
    }

}