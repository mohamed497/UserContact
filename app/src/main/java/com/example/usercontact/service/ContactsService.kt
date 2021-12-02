package com.example.usercontact.service

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.ContactsContract
import android.widget.Toast
import com.example.usercontact.viewmodel.ContactViewModel
import com.example.usercontact.database.UserModel

import com.example.usercontact.ui.contact.activity.ContactsActivity
import android.R
import android.app.*
import androidx.core.app.NotificationCompat

const val CHANNEL_ID = "ChannelId1"

class ContactsService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(
            applicationContext, "This is a Service running in Background",
            Toast.LENGTH_SHORT
        ).show()
//        createNotification()

//        val notificationIntent = Intent(this, ContactsActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0, notificationIntent, 0
//        )
//
//        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Example Service")
//            .setContentText("Our App is Running")
//            .setSmallIcon(R.drawable.btn_plus)
//            .setContentIntent(pendingIntent)
//            .build()
//
        fetchUsers(applicationContext)

//        startForeground(1, notification)

        return START_STICKY
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Example Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    @SuppressLint("Range")
    private fun fetchUsers(applicationContext: Context) {
        val cursor = applicationContext.contentResolver.query(
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
                val contactViewModel = ContactViewModel()
                contactViewModel.insert(contact)
            }

        }
    }
}