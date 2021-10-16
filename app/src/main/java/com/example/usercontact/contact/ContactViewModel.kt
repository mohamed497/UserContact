package com.example.usercontact.contact
import android.annotation.SuppressLint
import android.app.Application
import android.provider.ContactsContract
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.contacsusers.repo.Repo
import com.example.usercontact.database.UserModel
import kotlinx.coroutines.*

class ContactViewModel(private val repo: Repo,  application: Application): AndroidViewModel(application),Observable {

    private val context = application

    val contact= repo.contacts

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    val isVisibleClear = MutableLiveData<Boolean>()
    val isVisibleGet = MutableLiveData<Boolean>()


    fun clearAllContacts(){
        clearAll()
        isVisibleClear.value = false
        isVisibleGet.value = true
    }
    fun getAllContacts(){
        fetchContacts()
        isVisibleGet.value = false
    }

    fun insert(userModel: UserModel){
        uiScope.launch {
            withContext(Dispatchers.IO){
                repo.insert(userModel)

            }
        }
    }


    private fun clearAll(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                repo.delete()
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
            isVisibleClear.value = true
            isVisibleGet.value = false
        }

    }
}