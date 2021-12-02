package com.example.usercontact.repo


import com.example.usercontact.base.App
import com.example.usercontact.database.UserDatabase
import com.example.usercontact.database.UserModel

class UsersRepo {
    private val usersDao = UserDatabase.getInstance(App.instance).userDatabaseDao

    val contacts = usersDao.getAllContact()

    fun insert(userModel: UserModel){
        usersDao.insert(userModel)
    }

    fun delete(){
        usersDao.delete()
    }
    fun getContact(){
        usersDao.getContact()
    }

    fun getUser(userNumber: String): UserModel{
        return usersDao.get(userNumber)
    }
}