package com.example.usercontact.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_contact_table")
data class UserModel(

        @PrimaryKey(autoGenerate = false)
        var userNumber: String,

        @ColumnInfo(name = "user_name")
        var userName: String

)