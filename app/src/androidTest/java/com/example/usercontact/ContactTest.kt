package com.example.usercontact

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.usercontact.database.UserDatabase
import com.example.usercontact.database.UserDatabaseDao
import com.example.usercontact.database.UserModel
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ContactTest {
    private lateinit var userDao: UserDatabaseDao
    private lateinit var db: UserDatabase


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val user = UserModel("0121212", "mohamed")
        userDao.insert(user)
        val getContact = userDao.getContact()
        assertEquals(getContact?.userNumber, "0121212")
    }
}