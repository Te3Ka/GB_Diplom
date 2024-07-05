package ru.te3ka.boardgamerdiary.schedule_meeting.invitation

import android.Manifest
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.te3ka.boardgamerdiary.MainActivity
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentScheduleMeetingInvitationBinding
import ru.te3ka.boardgamerdiary.model.Meeting
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkProfile
import ru.te3ka.boardgamerdiary.schedule_meeting.ScheduleMeetingFragment
import ru.te3ka.boardgamerdiary.schedule_meeting.ScheduleMeetingFragment.Companion
import java.io.IOException

class InvitationFragment : Fragment() {
    private var _binding: FragmentScheduleMeetingInvitationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleMeetingInvitationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSendInvitation.setOnClickListener {
            createNotification()
        }
    }

    private fun createNotification() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        val intent = Intent(requireContext(), MainActivity::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.getActivity(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        else
            PendingIntent.getActivity(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val messageTitle = "BoardGamerDiary Invitation"
        val messageBody = "Пришло приглашение на встречу" +
                " ${binding.editTextDayWhensMeeting.text}/${binding.editTextMonthWhensMeeting.text}/${binding.editTextYearWhensMeeting.text}" +
                " в ${binding.editTextWheresMeeting.text}" +
                " с ${binding.editTextWhosMeetingWith.text}" +
                " и будем играть в ${binding.editTextWhatAreWePlaying.text}!"

        val notification =
            NotificationCompat.Builder(requireContext(), MainActivity.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.button_ic_schedule_meeting)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        sendInvitationToServer()

//        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
    }

    private fun sendInvitationToServer() {
        val meeting = Meeting(
            date = "${binding.editTextDayWhensMeeting.text}-${binding.editTextMonthWhensMeeting.text}-${binding.editTextYearWhensMeeting.text}",
            location = binding.editTextWheresMeeting.text.toString(),
            boardgames = listOf(binding.editTextWhatAreWePlaying.text.toString()), // Убедитесь, что у вас правильный формат
            contacts = listOf(binding.editTextWhosMeetingWith.text.toString()) // Убедитесь, что у вас правильный формат
        )

        Log.i(TAG, "Start sending invitation")
        val requestBody =
            Gson().toJson(meeting).toRequestBody("application/json; charset=UTF-8".toMediaTypeOrNull())
        println(requestBody.toString())
        val request = Request.Builder()
            .url("http://192.168.31.193:8080/meetings/") // Замените на ваш сервер
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    Log.i(TAG, "Invitation sent successfully")
                } else {
                    Log.e(TAG, "Failed to send invitation: ${response.code}")
                    response.body?.string()?.let { Log.e(TAG, it) } // Вывод тела ответа для отладки
                }
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                createNotification()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permission for notifications was denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 10_000
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
    }
}