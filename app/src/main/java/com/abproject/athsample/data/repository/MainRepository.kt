package com.abproject.athsample.data.repository

import com.abproject.athsample.data.dataclass.User
import com.abproject.athsample.data.mail.Mail

/**
 * Created by Abolfazl on 5/13/21
 */
interface MainRepository {
    suspend fun insertUser(user: User): Boolean
    suspend fun updateUser(user: User)
    suspend fun getUsers(): List<User>
    suspend fun searchInUsersByUsername(query: String): User?
    suspend fun searchInUsersByEmail(email: String): User?
    fun loadUserExisting()
    fun loadUserInformation(user: User? = null)
    fun saveUserInformationInSharedPrefs(user: User)
    fun clearDataFromSharedPrefs()
    fun clearDataFromUserInformationDataClass()
    fun checkExistingUserInSharedPrefs(): Boolean
}