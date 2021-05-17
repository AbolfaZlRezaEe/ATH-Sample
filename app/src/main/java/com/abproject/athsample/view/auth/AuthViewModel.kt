package com.abproject.athsample.view.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abp.noties.base.ATHViewModel
import com.abproject.athsample.data.database.UserDao
import com.abproject.athsample.data.dataclass.User
import com.abproject.athsample.data.dataclass.UserInformation
import com.abproject.athsample.util.EncryptionTools
import com.abproject.athsample.util.Variables.EMAIL_KEY
import com.abproject.athsample.util.Variables.FIRSTNAME_KEY
import com.abproject.athsample.util.Variables.IS_EXISTING_KEY
import com.abproject.athsample.util.Variables.LASTNAME_KEY
import com.abproject.athsample.util.Variables.USERNAME_KEY
import kotlinx.coroutines.*
import timber.log.Timber

class AuthViewModel(
    private val dao: UserDao,
    private val sharedPreferences: SharedPreferences
) : ATHViewModel() {

    private val _checkUserInformationResult = MutableLiveData<Boolean>()
    private val _saveUserInformationStatus = MutableLiveData<Boolean>()
    val checkUserInformationResult: LiveData<Boolean> = _checkUserInformationResult
    val saveUserInformationStatus: LiveData<Boolean> = _saveUserInformationStatus

    fun saveUserInformation(
        context: Context,
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        phoneNumber: String,
        password: String,
        createAt: String
    ) {
        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            username = username,
            password = password,
            phoneNumber = phoneNumber,
            createAt = createAt
        )
        viewModelScope.launch {
            val checkUsernameIsExist = dao.searchInUsersByUsername(username)
            if (checkUsernameIsExist != null) {
                _saveUserInformationStatus.postValue(false)
                return@launch
            }
            val encryptedPassword = EncryptionTools(context).encryptRSA(password)
            user.password = encryptedPassword
            dao.upsertUser(user)
            saveUserInformationInSharedPrefs(user)
            _saveUserInformationStatus.postValue(true)
        }
    }

    private suspend fun saveUserInformationInSharedPrefs(user: User) =
        withContext(Dispatchers.Main) {
            sharedPreferences.edit().apply {
                putBoolean(IS_EXISTING_KEY, true)
                putString(USERNAME_KEY, user.username)
                putString(EMAIL_KEY, user.email)
                putString(FIRSTNAME_KEY, user.firstName)
                putString(LASTNAME_KEY, user.lastName)
            }.apply()
            loadUserInformation(user)
        }

    fun checkUserInformation(
        username: String,
        password: String,
        context: Context
    ) = viewModelScope.launch {
        val result = dao.searchInUsersByUsername(username)
        if (result != null) {
            val decryptedPassword = EncryptionTools(context).decryptRSA(result.password)
            saveUserInformationInSharedPrefs(result)
            _checkUserInformationResult.postValue(decryptedPassword == password)
        }
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