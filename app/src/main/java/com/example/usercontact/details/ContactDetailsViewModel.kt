package com.example.usercontact.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usercontact.repo.UsersRepo
import com.example.usercontact.database.UserModel
import kotlinx.coroutines.*

class ContactDetailsViewModel(private val repo: UsersRepo, userNumber: String) : ViewModel() {


    private val _userContact = MutableLiveData<UserModel>()
    val userContact: LiveData<UserModel>
        get() = _userContact


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getUser(userNumber)
    }


    private fun getUser(userNumber: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                repo.getUser(userNumber)
                _userContact.postValue(repo.getUser(userNumber))
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}




