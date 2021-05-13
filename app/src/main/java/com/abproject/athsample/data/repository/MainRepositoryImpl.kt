package com.abproject.athsample.data.repository

import com.abproject.athsample.data.database.UserDao
import com.abproject.athsample.data.dataclass.User

class MainRepositoryImpl(private val userDao: UserDao) : MainRepository {
    override suspend fun upsertUser(user: User): Long {
        return userDao.upsertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        return userDao.deleteUser(user)
    }

    override suspend fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    override suspend fun searchInUsersByUsername(query: String): List<User> {
        return userDao.searchInUsersByUsername(query)
    }
}