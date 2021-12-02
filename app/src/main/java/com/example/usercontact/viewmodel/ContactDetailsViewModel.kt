package com.example.usercontact.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usercontact.base.BaseViewModel
import com.example.usercontact.repo.UsersRepo
import com.example.usercontact.database.UserModel
import kotlinx.coroutines.*

class ContactDetailsViewModel : BaseViewModel() {

    private val userRepo = UsersRepo()

    private val _userContact = MutableLiveData<UserModel>()
    val userContact: LiveData<UserModel>
        get() = _userContact


    fun getUser(userNumber: String) {
        super.uiScope.launch {
            withContext(Dispatchers.IO) {
                userRepo.getUser(userNumber)
                _userContact.postValue(userRepo.getUser(userNumber))
            }
        }

    }

}




