package ru.te3ka.boardgamerdiary.schedule_meeting.invitation

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
import ru.te3ka.boardgamerdiary.MainActivity
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentScheduleMeetingInvitationBinding
import ru.te3ka.boardgamerdiary.schedule_meeting.ScheduleMeetingFragment
import ru.te3ka.boardgamerdiary.schedule_meeting.ScheduleMeetingFragment.Companion

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
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.POST_NOTIFICATIONS),
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

        val notification = NotificationCompat.Builder(requireContext(), MainActivity.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.button_ic_schedule_meeting)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                createNotification()
            } else {
                Toast.makeText(requireContext(), "Permission for notifications was denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 10_000
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
    }
}