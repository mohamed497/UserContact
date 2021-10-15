package com.example.usercontact.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userModel: UserModel): Long

    @Query("DELETE FROM user_contact_table")
    fun delete()

    @Query("SELECT * FROM user_contact_table")
    fun getAllContact(): LiveData<List<UserModel>>


}