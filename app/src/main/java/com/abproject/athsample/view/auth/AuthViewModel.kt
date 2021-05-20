package com.abproject.athsample.view.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abproject.athsample.base.ATHViewModel
import com.abproject.athsample.data.dataclass.User
import com.abproject.athsample.data.mail.Mail
import com.abproject.athsample.data.mail.SendEmailApi
import com.abproject.athsample.data.repository.MainRepository
import com.abproject.athsample.util.EncryptionTools
import com.abproject.athsample.util.Resource
import kotlinx.coroutines.*

/**
 * Created by Abolfazl on 5/15/21
 */
class AuthViewModel(
    private val mainRepository: MainRepository
) : ATHViewModel() {

    private val _checkUserInformationResult = MutableLiveData<Boolean>()
    private val _saveUserInformationStatus = MutableLiveData<Boolean>()
    private val _resetPasswordStatus = MutableLiveData<Resource<Boolean>>()
    val checkUserInformationResult: LiveData<Boolean> = _checkUserInformationResult
    val saveUserInformationStatus: LiveData<Boolean> = _saveUserInformationStatus
    val resetPasswordStatus: LiveData<Resource<Boolean>> get() = _resetPasswordStatus

    fun saveUserInformation(
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
            val result = mainRepository.insertUser(user)
            _saveUserInformationStatus.postValue(result)
        }
    }

    fun changeUserPasswordAndUpdate(email: String, newPassword: String) = viewModelScope.launch {
        _resetPasswordStatus.postValue(Resource.Loading())
        val result = mainRepository.searchInUsersByEmail(email)
        if (result != null) {
            result.password = newPassword
            mainRepository.updateUser(result)
            _resetPasswordStatus.postValue(Resource.Success(true, null))
        }
    }


    fun checkUserInformation(
        username: String,
        password: String,
        context: Context
    ) = viewModelScope.launch {
        val result = mainRepository.searchInUsersByUsername(username)
        if (result != null) {
            val decryptedPassword = EncryptionTools(context).decryptRSA(result.password)
            mainRepository.saveUserInformationInSharedPrefs(result)
            if (decryptedPassword == password) {
                _checkUserInformationResult.postValue(true)
                mainRepository.loadUserExisting()
                mainRepository.loadUserInformation(result)
            } else
                _checkUserInformationResult.postValue(false)

        }
    }

    fun sendEmailToUser(userEmail: String): LiveData<Resource<String>> {
        val sendEmailApi = SendEmailApi()
        viewModelScope.launch {
            val result = mainRepository.searchInUsersByEmail(userEmail)
            if (result != null) {
                sendEmailApi.sendEmail(userEmail)
            } else
                sendEmailApi._emailStatus.postValue(
                    Resource.Error(
                        null,
                        "Please enter a valid email!"
                    )
                )
        }
        return sendEmailApi.emailStatus
    }
}