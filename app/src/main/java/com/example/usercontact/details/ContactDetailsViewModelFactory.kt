package com.example.usercontact.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usercontact.repo.UsersRepo

class ContactDetailsViewModelFactory(private val repo: UsersRepo, private val userNumber: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactDetailsViewModel::class.java)) {
            return ContactDetailsViewModel(repo, userNumber) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}