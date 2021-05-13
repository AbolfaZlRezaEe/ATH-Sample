package com.abproject.athsample.data.database

import androidx.room.*
import com.abproject.athsample.data.dataclass.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: User): Long

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM tbl_user")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM tbl_user WHERE username LIKE '%' || :query || '%'")
    suspend fun searchInUsersByUsername(query: String): List<User>

}