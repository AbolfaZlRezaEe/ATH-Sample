package com.abproject.athsample.data.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val phoneNumber: String,
    val password: String,
    val createAt: String
)
