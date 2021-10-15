package com.example.usercontact

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.contacsusers.ContactAdapter
import com.example.android.contacsusers.repo.Repo
import com.example.usercontact.contact.ContactViewModel
import com.example.usercontact.contact.ContactViewModelFactory
import com.example.usercontact.database.UserDatabase
import com.example.usercontact.database.UserDatabaseDao
import com.example.usercontact.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {


    private lateinit var binding : ActivityContactBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)
        val dao: UserDatabaseDao = UserDatabase.getInstance(application).userDatabaseDao
        val repo = Repo(dao)
        val factory = ContactViewModelFactory(repo, application)
        contactViewModel = ViewModelProvider(this, factory).get(ContactViewModel::class.java)

        binding.viewModel= contactViewModel
        binding.lifecycleOwner = this

        displayContactsList()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED){
            contactViewModel.fetchContacts()
        }
    }

    private fun displayContactsList() {
        contactViewModel.contact.observe(this, Observer {
                val adapter = ContactAdapter()
                binding.contactRecyclerView.adapter = adapter
        })
    }

    override fun onResume() {
        super.onResume()
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, Array(1){ Manifest.permission.READ_CONTACTS}, 111)
        }else{
            contactViewModel.fetchContacts()
        }
    }
}