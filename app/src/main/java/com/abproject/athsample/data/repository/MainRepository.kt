package com.abproject.athsample.data.repository

import com.abproject.athsample.data.dataclass.User

/**
 * Created by Abolfazl on 5/13/21
 */
interface MainRepository {
    suspend fun upsertUser(user: User): Boolean
    suspend fun getUsers(): List<User>
    suspend fun searchInUsersByUsername(query: String): User?
    fun loadUserExisting()
    fun loadUserInformation(user: User? = null)
    fun saveUserInformationInSharedPrefs(user: User)
    fun clearDataFromSharedPrefs()
    fun checkExistingUserInSharedPrefs(): Boolean
}