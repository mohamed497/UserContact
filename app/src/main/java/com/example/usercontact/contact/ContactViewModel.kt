package com.example.usercontact.contact

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.contacsusers.repo.Repo
import com.example.usercontact.database.UserModel
import kotlinx.coroutines.*

//class ContactViewModel(private val  userKey: Long= 0,
//    val database: UserDatabaseDao): ViewModel() {
class ContactViewModel(private val repo: Repo,  application: Application): AndroidViewModel(application),Observable {

    val context = application

    val contact= repo.contacts

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)



    fun clearAllContacts(){
        clearAll()
    }
    fun getAllContacts(){
        fetchContacts()
    }


    fun insert(userModel: UserModel): Job{
        return ( viewModelScope.launch {
            repo.insert(userModel)
        })
    }


    private fun clearAll(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                repo.delete()
            }
        }
    }

    private fun getAll(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                repo.contacts
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    @SuppressLint("Range")
    fun fetchContacts(){

        val cursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null)
        if (cursor != null ) {
            while (cursor?.moveToNext()!!) {

                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val contact =UserModel(phone,  name)

                insert(contact)
            }
        }

    }
}