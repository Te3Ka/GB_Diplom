package ru.te3ka.boardgamerdiary.fcmservice

import android.Manifest
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.te3ka.boardgamerdiary.MainActivity
import ru.te3ka.boardgamerdiary.R
import java.util.Date
import java.util.Locale
import kotlin.random.Random

/**
 * Сервис для обработки сообщений Firebase Cloud Messaging (FCM).
 * Отвечает за получение сообщений и отображение уведомлений.
 */
class FcmService : FirebaseMessagingService() {
    /**
     * Обрабатывает входящее сообщение от Firebase.
     * Создает и отображает уведомление на основе данных сообщения.
     * @param message Сообщение от Firebase.
     */
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

    /**
     * Обрабатывает получение нового токена для FCM.
     * Отправляет токен на сервер для обновления.
     * @param token Новый токен.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendTokenToServer(token)
    }

    /**
     * Отправляет токен на сервер для обновления.
     * @param token Токен для отправки.
     */
    private fun sendTokenToServer(token: String) {
        // TODO: Сделать отправку токена на сервер.
    }

    /**
     * Преобразует временную метку в строку с датой и временем.
     * @param timestamp Временная метка в формате UNIX.
     * @return Строка с датой и временем.
     */
    private fun convertToDate(timestamp: String?) : String {
        timestamp ?: return ""
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(timestamp.toLong() * 1000))
    }
}