package com.example.usercontact.baseviewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {

     private val viewModelJob = Job()
     val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

     public override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}