package com.abproject.athsample.data.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Abolfazl on 5/13/21
 */
@Entity(tableName = "tbl_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val phoneNumber: String,
    var password: String,
    val createAt: String
)
