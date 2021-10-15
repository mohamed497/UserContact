package com.example.android.contacsusers.repo


import com.example.usercontact.database.UserDatabaseDao
import com.example.usercontact.database.UserModel

class Repo(private val userDatabaseDao: UserDatabaseDao) {

    val contacts = userDatabaseDao.getAllContact()

    suspend fun insert(userModel: UserModel){
        userDatabaseDao.insert(userModel)
    }

    suspend fun delete(){
        userDatabaseDao.delete()
    }

}