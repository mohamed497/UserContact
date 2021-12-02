package com.example.usercontact.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usercontact.base.BaseViewModel
import com.example.usercontact.repo.UsersRepo
import com.example.usercontact.database.UserModel
import kotlinx.coroutines.*

class ContactViewModel :
    BaseViewModel() {

    private val userRepo = UsersRepo()
    val contact = userRepo.contacts

    private val _navigateToSelectedUser = MutableLiveData<String>()
    val navigateToSelectedUser: LiveData<String>
        get() = _navigateToSelectedUser


    val isVisibleClear = MutableLiveData<Boolean>()
    val isVisibleGet = MutableLiveData<Boolean>()


    fun clearAllContacts() {
        clearAll()
        isVisibleClear.value = false
        isVisibleGet.value = true
    }

    fun getAllContacts(context: Context) {
        fetchContacts(context)
        isVisibleGet.value = false
    }

     fun insert(userModel: UserModel) {
        super.uiScope.launch {
            withContext(Dispatchers.IO) {
                userRepo.insert(userModel)

            }
        }
    }


    private fun clearAll() {
        super.uiScope.launch {
            withContext(Dispatchers.IO) {
                userRepo.delete()
            }
        }
    }


    @SuppressLint("Range")
    fun fetchContacts(context: Context) {

        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )
        if (cursor != null) {
            while (cursor?.moveToNext()!!) {
                ContactsContract.RawContacts.VERSION

                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phone =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val contact = UserModel(phone, name)

                insert(contact)
            }
            isVisibleClear.value = true
            isVisibleGet.value = false
        }

    }

    fun displayPropertyDetails(userNumber: String) {
        _navigateToSelectedUser.value = userNumber
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedUser.value = null
    }
}