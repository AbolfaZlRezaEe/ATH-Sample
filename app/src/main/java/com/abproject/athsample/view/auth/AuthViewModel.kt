package com.abproject.athsample.view.auth

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.abp.noties.base.ATHViewModel
import com.abproject.athsample.data.database.UserDao
import com.abproject.athsample.data.dataclass.User
import com.abproject.athsample.data.dataclass.UserInformation
import com.abproject.athsample.data.dataclass.UserInformation.email
import com.abproject.athsample.data.dataclass.UserInformation.isExisting
import com.abproject.athsample.util.Variables.EMAIL_KEY
import com.abproject.athsample.util.Variables.FIRSTNAME_KEY
import com.abproject.athsample.util.Variables.IS_EXISTING_KEY
import com.abproject.athsample.util.Variables.LASTNAME_KEY
import com.abproject.athsample.util.Variables.USERNAME_KEY
import kotlinx.coroutines.launch
import timber.log.Timber

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
        password: String,
        createAt: String
    ): Boolean {
        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            username = username,
            password = password,
            phoneNumber = phoneNumber,
            createAt = createAt
        )
        return if (userIsExisting(username))
            false
        else {
            viewModelScope.launch {
                dao.upsertUser(user)
            }
            sharedPreferences.edit().apply {
                putBoolean(IS_EXISTING_KEY, true)
                putString(USERNAME_KEY, user.username)
                putString(EMAIL_KEY, user.email)
                putString(FIRSTNAME_KEY, user.firstName)
                putString(LASTNAME_KEY, user.lastName)
            }.apply()
            loadUserInformation(user)
            true
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
            Timber.i(users.size.toString())
            return users.isNotEmpty()
        }
        return false
    }

    fun loadUserExisting() {
        UserInformation.updateIsExisting(
            sharedPreferences.getBoolean(IS_EXISTING_KEY, false)
        )
    }

    fun loadUserInformation(user: User? = null) {
        user?.let { information ->
            UserInformation.updateUserInformation(
                username = information.username,
                email = information.email,
                firstName = information.firstName,
                lastName = information.lastName
            )
        }
        if (user == null) {
            UserInformation.updateUserInformation(
                username = notNullStringFromSharedPrefs(sharedPreferences, USERNAME_KEY),
                email = notNullStringFromSharedPrefs(sharedPreferences, EMAIL_KEY),
                firstName = notNullStringFromSharedPrefs(sharedPreferences, FIRSTNAME_KEY),
                lastName = notNullStringFromSharedPrefs(sharedPreferences, LASTNAME_KEY)
            )
        }

    }

}