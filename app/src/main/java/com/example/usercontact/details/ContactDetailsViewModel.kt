package com.example.usercontact.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usercontact.baseviewmodel.BaseViewModel
import com.example.usercontact.repo.UsersRepo
import com.example.usercontact.database.UserModel
import kotlinx.coroutines.*

class ContactDetailsViewModel(private val repo: UsersRepo) : BaseViewModel() {


    private val _userContact = MutableLiveData<UserModel>()
    val userContact: LiveData<UserModel>
        get() = _userContact


    fun getUser(userNumber: String) {
        super.uiScope.launch {
            withContext(Dispatchers.IO) {
                repo.getUser(userNumber)
                _userContact.postValue(repo.getUser(userNumber))
            }
        }

    }

}




