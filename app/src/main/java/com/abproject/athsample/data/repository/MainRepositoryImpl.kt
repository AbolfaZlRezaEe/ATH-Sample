package com.abproject.athsample.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.abproject.athsample.data.database.UserDao
import com.abproject.athsample.data.dataclass.User
import com.abproject.athsample.data.dataclass.UserInformation
import com.abproject.athsample.util.EncryptionTools
import com.abproject.athsample.util.Variables

/**
 * Created by Abolfazl on 5/13/21
 */
class MainRepositoryImpl(
    private val context: Context,
    private val userDao: UserDao,
    private val sharedPreferences: SharedPreferences
) : MainRepository {

    override suspend fun insertUser(user: User): Boolean {
        val checkUsernameIsExist = userDao.searchInUsersByUsername(user.username)
        if (checkUsernameIsExist != null)
            return false

        val encryptedPassword = EncryptionTools(context).encryptRSA(user.password)
        user.password = encryptedPassword
        userDao.insertUser(user)
        saveUserInformationInSharedPrefs(user)
        loadUserInformation(user)
        return true
    }

    override suspend fun updateUser(user: User) {
        user.password = EncryptionTools(context).encryptRSA(user.password)
        userDao.updateUser(user)
        clearDataFromSharedPrefs()
        clearDataFromUserInformationDataClass()
        saveUserInformationInSharedPrefs(user)
        loadUserInformation(user)
    }


    override suspend fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    override suspend fun searchInUsersByUsername(query: String): User? {
        return userDao.searchInUsersByUsername(query)
    }

    override suspend fun searchInUsersByEmail(email: String): User? {
        return userDao.searchInUsersByEmail(email)
    }

    override fun loadUserExisting() {
        UserInformation.updateIsExisting(
            sharedPreferences.getBoolean(Variables.IS_EXISTING_KEY, false)
        )
    }

    override fun loadUserInformation(user: User?) {
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
                username = notNullStringFromSharedPrefs(
                    sharedPreferences,
                    Variables.USERNAME_KEY
                ),
                email = notNullStringFromSharedPrefs(
                    sharedPreferences,
                    Variables.EMAIL_KEY
                ),
                firstName = notNullStringFromSharedPrefs(
                    sharedPreferences,
                    Variables.FIRSTNAME_KEY
                ),
                lastName = notNullStringFromSharedPrefs(
                    sharedPreferences,
                    Variables.LASTNAME_KEY
                )
            )
        }
    }

    override fun saveUserInformationInSharedPrefs(user: User) {
        sharedPreferences.edit().apply {
            putBoolean(Variables.IS_EXISTING_KEY, true)
            putString(Variables.USERNAME_KEY, user.username)
            putString(Variables.EMAIL_KEY, user.email)
            putString(Variables.FIRSTNAME_KEY, user.firstName)
            putString(Variables.LASTNAME_KEY, user.lastName)
        }.apply()
    }

    override fun clearDataFromSharedPrefs() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }

    override fun clearDataFromUserInformationDataClass() {
        UserInformation.clearInformation()
    }

    override fun checkExistingUserInSharedPrefs(): Boolean {
        return sharedPreferences.getBoolean(Variables.IS_EXISTING_KEY, false)
    }

    /**
     * it's a custom function for sharedPrefs getString method
     * getString @return null if that key doesn't exist, this function
     * take that null and return an empty string if it's null.
     */
    private fun notNullStringFromSharedPrefs(
        sharedPreferences: SharedPreferences,
        key: String,
        defaultValue: String? = null
    ): String {
        val response = sharedPreferences.getString(key, defaultValue)
        return response ?: ""
    }

}