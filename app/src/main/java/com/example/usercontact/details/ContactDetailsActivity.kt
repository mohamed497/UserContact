package com.example.usercontact.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.usercontact.R
import com.example.usercontact.repo.UsersRepo
import com.example.usercontact.database.UserDatabase
import com.example.usercontact.database.UserDatabaseDao
import kotlinx.android.synthetic.main.activity_details_contact.*


class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var detailsViewModel: ContactDetailsViewModel
    private lateinit var repo: UsersRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_contact)


        val userNumber = intent.getStringExtra(CONTACT_ID_ARG)
        if (userNumber != null) {
            initData(userNumber)
            setupObservers()
        }

    }

    companion object {
        private const val CONTACT_ID_ARG = "contact_id"
        fun createIntentForContactId(contactId: String, context: Context): Intent =
            Intent(context, ContactDetailsActivity::class.java).apply {
                putExtra(CONTACT_ID_ARG, contactId)
            }
    }

    private fun setupObservers() {
        detailsViewModel.userContact.observe(this, Observer { userModel ->
            name.text = userModel.userName
            number.text = userModel.userNumber
            Log.d(ContactDetailsActivity::class.simpleName, userModel.userName)
        })
    }

    private fun initData(userNumber: String) {
        val dao: UserDatabaseDao = UserDatabase.getInstance(application).userDatabaseDao
        repo = UsersRepo(dao)
        val factory = ContactDetailsViewModelFactory(repo)
        detailsViewModel =
            ViewModelProvider(this, factory).get(ContactDetailsViewModel::class.java)
        detailsViewModel.getUser(userNumber)

    }

    override fun onDestroy() {
        super.onDestroy()
        detailsViewModel.onCleared()
    }
}