package ru.te3ka.boardgamerdiary.fcmservice

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import ru.te3ka.boardgamerdiary.MainActivity
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.model.Profile
import java.io.IOException
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class FcmService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notification = NotificationCompat.Builder(this, MainActivity.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.button_ic_schedule_meeting)
            .setContentTitle(message.data["nickname"])
            .setContentText(message.data["message"] + convertToDate(message.data["timestamp"]))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(this).notify(Random.nextInt(), notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {

    }

    private fun convertToDate(timestamp: String?) : String {
        timestamp ?: return ""
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(timestamp.toLong() * 1000))
    }
}