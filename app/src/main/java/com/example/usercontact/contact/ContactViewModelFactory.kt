package com.example.usercontact.contact

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.contacsusers.repo.Repo

//class ContactViewModelFactory(
//        private val userKey: Long,
//        private val dataSource: UserDatabaseDao
//): ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ContactViewModel::class.java)){
//            return ContactViewModel(userKey, dataSource) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}

class ContactViewModelFactory(private val repo: Repo , application: Application): ViewModelProvider.Factory{
    val context = application
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)){
            return ContactViewModel(repo, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}