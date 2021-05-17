package com.abproject.athsample.data.repository

import com.abproject.athsample.data.dataclass.User

interface MainRepository {
    suspend fun upsertUser(user: User): Long
    suspend fun deleteUser(user: User)
    suspend fun getUsers(): List<User>
    suspend fun searchInUsersByUsername(query: String): User?
}