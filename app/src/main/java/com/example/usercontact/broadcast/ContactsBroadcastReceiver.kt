package com.example.usercontact.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.usercontact.service.ContactsService
import com.example.usercontact.ui.contact.activity.ContactsActivity

class ContactsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Toast.makeText(context, "STARTED", Toast.LENGTH_LONG).show()
        val action = intent.action

//        if (Intent.ACTION_BOOT_COMPLETED == action) {
//            val serviceIntent = Intent(context, ContactsService::class.java)
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(serviceIntent)
//            }
//        }
    }
}