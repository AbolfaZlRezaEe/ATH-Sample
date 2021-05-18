package com.abproject.athsample.data.database

import androidx.room.*
import com.abproject.athsample.data.dataclass.User

/**
 * Created by Abolfazl on 5/13/21
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM tbl_user")
    suspend fun getUsers(): List<User>

    /**
     * this function takes a username for searching in database.
     * if username has already exists @return User and if this
     * username didn't exist so @return null
     */
    @Query("SELECT * FROM tbl_user WHERE username LIKE '%' || :query || '%'")
    suspend fun searchInUsersByUsername(query: String): User?

}