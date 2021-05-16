package com.abproject.athsample.view.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abp.noties.base.ATHViewModel
import com.abproject.athsample.data.database.UserDao
import com.abproject.athsample.data.dataclass.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val dao: UserDao,
    private val sharedPreferences: SharedPreferences
) : ATHViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        fatchDataFromDb()
    }

    private fun fatchDataFromDb() = viewModelScope.launch {
        val response = dao.getUsers()
        withContext(Dispatchers.Main) {
            _users.value = response
        }
    }

    fun clearDataFromStorage() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }

}