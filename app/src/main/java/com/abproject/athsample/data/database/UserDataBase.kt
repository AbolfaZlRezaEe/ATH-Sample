package com.abproject.athsample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abproject.athsample.data.dataclass.User

/**
 * Created by Abolfazl on 5/13/21
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {
    abstract val userDao: UserDao
}