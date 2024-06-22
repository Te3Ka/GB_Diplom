package ru.te3ka.boardgamerdiary

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.databinding.ActivityMainBinding
import ru.te3ka.boardgamerdiary.db.BgdDatabase
import ru.te3ka.boardgamerdiary.model.Contact
import ru.te3ka.boardgamerdiary.model.MyCollection
import ru.te3ka.boardgamerdiary.model.Profile
import ru.te3ka.boardgamerdiary.model.WantToPlay
import ru.te3ka.boardgamerdiary.model.Wishlist

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        createNotificationChannel()
        setContentView(binding.root)
        initialiseProfile()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Test notification channel"
        val descriptionText = "Test push message"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            name,
            importance
        ).apply {
            description = descriptionText
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun initialiseProfile() {
        val db = BgdDatabase.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            val profileDao = db.profileDao()
            val profileExists = profileDao.getProfile() != null

            if (!profileExists) {
                val contactDao = db.contactDao()
                val myCollectionDao = db.myCollectionDao()
                val wishlistDao = db.wishlistDao()
                val wantToPlayDao = db.wantToPlayDao()

                val contactId = contactDao.insertContact(
                    Contact(
                        phone = "",
                        nickname = "",
                        firstName = "",
                        surname = ""
                    )
                ).toString().toInt()

                val myCollectionId = myCollectionDao.insertMyCollection(
                    MyCollection(
                        name = "",
                        score = "",
                        numberOfGames = "",
                        yearOfPurchase = "",
                        monthOfPurchase = ""
                    )
                ).toString().toInt()

                val wishlistId = wishlistDao.insertWishlist(
                    Wishlist(
                        name = "",
                    )
                ).toString().toInt()

                val wantToPlayId = wantToPlayDao.insertWantToPlay(
                    WantToPlay(
                        name = "",
                    )
                ).toString().toInt()

                val defaultProfile = Profile(
                    contactId = contactId,
                    myCollectionId = myCollectionId,
                    wishlistId = wishlistId,
                    wantToPlayId = wantToPlayId,
                    contactPhone = "",
                    nickname = "",
                    firstName = "",
                    surname = "",
                    city = "",
                    email = "",
                    hobbies = "",
                    dayOfBirth = 1,
                    monthOfBirth = 0,
                    yearOfBirth = 2000,
                    photoPath = ""
                )
                profileDao.insertProfile(defaultProfile)
            }
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "test_channel_id"
    }
}