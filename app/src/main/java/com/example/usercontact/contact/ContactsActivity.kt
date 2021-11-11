package com.example.usercontact.contact

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.usercontact.R
import com.example.usercontact.repo.UsersRepo
import com.example.usercontact.database.UserDatabase
import com.example.usercontact.database.UserDatabaseDao
import com.example.usercontact.databinding.ActivityContactsBinding
import com.example.usercontact.details.ContactDetailsActivity
import kotlinx.android.synthetic.main.activity_contacts.*

private const val READ_CONTACTS_PERMISSION_REQUEST_CODE = 111

class ContactActivity : AppCompatActivity() {


    private lateinit var binding: ActivityContactsBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts)

        initNeededData()
        setupBinding()
        displayContactsList()

        btn_add.setOnClickListener {
            contactViewModel.getAllContacts(this)
        }
        btn_remove.setOnClickListener {
            contactViewModel.clearAllContacts()
        }
    }

    private fun initNeededData() {
        val dao: UserDatabaseDao = UserDatabase.getInstance(application).userDatabaseDao
        val repo = UsersRepo(dao)
        val factory = ContactViewModelFactory(repo)
        contactViewModel = ViewModelProvider(this, factory).get(ContactViewModel::class.java)
    }


    private fun fetchData() {
        contactViewModel.fetchContacts(this)
    }

    private fun setupBinding() {
        binding.viewModel = contactViewModel
        binding.lifecycleOwner = this
    }

    private fun isReadContactsPermissionGranted(): Boolean =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (isReadContactsPermissionGranted()) {
            fetchData()
        }
    }


    private fun displayContactsList() {
        contactViewModel.contact.observe(this, Observer {
            val adapter = ContactAdapter(OnClickListener { contactId ->
                openContactDetailsScreen(contactId)
            })

            contactViewModel.navigateToSelectedUser.observe(this, Observer { contactId ->
                if (contactId != null) {
                    navigateContactDetails(contactId)
                }
            })
            contact_recyclerView.adapter = adapter
        })
    }

    private fun openContactDetailsScreen(contactId: String) {
        contactViewModel.displayPropertyDetails(contactId)
    }

    private fun navigateContactDetails(contactId: String) {

        val intent = ContactDetailsActivity.createIntentForContactId(
            contactId = contactId,
            context = ContactActivity@ this
        )
        startActivity(intent)
        contactViewModel.displayPropertyDetailsComplete()

    }


    override fun onResume() {
        super.onResume()
        if (isReadContactsPermissionGranted()) {
            requestReadContactsPermission()
        } else {
            fetchData()
        }
    }


    private fun requestReadContactsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            READ_CONTACTS_PERMISSION_REQUEST_CODE
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        contactViewModel.onCleared()
    }
}