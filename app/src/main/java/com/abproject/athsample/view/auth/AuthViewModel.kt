package com.abproject.athsample.view.auth

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.abp.noties.base.ATHViewModel
import com.abproject.athsample.data.database.UserDao
import com.abproject.athsample.data.dataclass.User
import com.abproject.athsample.data.dataclass.UserExisting
import com.abproject.athsample.data.dataclass.UserExisting.isExisting
import com.abproject.athsample.util.Variables.IS_EXISTING_KEY
import kotlinx.coroutines.launch

class AuthViewModel(
    private val dao: UserDao,
    private val sharedPreferences: SharedPreferences
) : ATHViewModel() {

    fun saveUserInformation(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        phoneNumber: String,
        password: String
    ): Boolean {
        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            username = username,
            password = password,
            phoneNumber = phoneNumber
        )
        return if (userIsExisting(username))
            false
        else {
            viewModelScope.launch {
                dao.upsertUser(user)
            }
            sharedPreferences.edit().apply {
                putBoolean(IS_EXISTING_KEY, true)
            }.apply()
            true
        }
    }

    fun checkUsers() {
        viewModelScope.launch {
            val users = dao.getUsers()
            if (users.isNotEmpty())
                sharedPreferences.edit().apply {
                    putBoolean(IS_EXISTING_KEY, true)
                }.apply()
            else
                sharedPreferences.edit().apply {
                    putBoolean(IS_EXISTING_KEY, false)
                }.apply()
        }
    }

    fun userIsExisting(
        username: String
    ): Boolean {
        var users: List<User> = listOf()
        if (isExisting) {
            viewModelScope.launch {
                users = dao.searchInUsersByUsername(username)
            }
            return users.isNotEmpty()
        }
        return false
    }

    fun loadUserExisting() {
        UserExisting.update(
            sharedPreferences.getBoolean(IS_EXISTING_KEY, false)
        )
    }

}