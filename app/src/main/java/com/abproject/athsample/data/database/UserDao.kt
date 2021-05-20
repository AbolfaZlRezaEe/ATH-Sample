package com.abproject.athsample.data.database

import androidx.room.*
import com.abproject.athsample.data.dataclass.User

/**
 * Created by Abolfazl on 5/13/21
 */
@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM tbl_user")
    suspend fun getUsers(): List<User>

    /**
     * this function takes a username for searching in database.
     * if username has already exists @return User and if this
     * username didn't exist so @return null
     */
    @Query("SELECT * FROM tbl_user WHERE username == :usernameString")
    suspend fun searchInUsersByUsername(usernameString: String): User?

    /**
     * this function takes a email for searching in database.
     * if email has already exists @return User and if this
     * email didn't exist so @return null
     */
    @Query("SELECT * FROM tbl_user WHERE email == :email")
    suspend fun searchInUsersByEmail(email: String): User?

}