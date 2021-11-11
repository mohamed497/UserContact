package com.example.usercontact.contact

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usercontact.repo.UsersRepo

class ContactViewModelFactory(private val repo: UsersRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}