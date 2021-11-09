package com.example.usercontact.repo


import com.example.usercontact.database.UserDatabaseDao
import com.example.usercontact.database.UserModel

class UsersRepo(private val userDatabaseDao: UserDatabaseDao) {

    val contacts = userDatabaseDao.getAllContact()

    fun insert(userModel: UserModel){
        userDatabaseDao.insert(userModel)
    }

    fun delete(){
        userDatabaseDao.delete()
    }
    fun getContact(){
        userDatabaseDao.getContact()
    }

    fun getUser(userNumber: String): UserModel{
        return userDatabaseDao.get(userNumber)
    }
}