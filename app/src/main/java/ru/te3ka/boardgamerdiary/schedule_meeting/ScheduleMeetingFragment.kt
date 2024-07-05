package ru.te3ka.boardgamerdiary.schedule_meeting

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import ru.te3ka.boardgamerdiary.MainActivity
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentScheduleMeetingBinding

/**
 * Фрагмент для планирования встреч.
 * Позволяет пользователю отправлять приглашения на встречи и просматривать уведомления.
 */
class ScheduleMeetingFragment : Fragment() {
    private var _binding: FragmentScheduleMeetingBinding? = null
    private val binding get() = _binding!!
    private lateinit var animationSlideRightIn: Animation

    private lateinit var viewModel: ScheduleMeetingViewModel

    /**
     * Создает и возвращает вид фрагмента.
     * @param inflater LayoutInflater для инфлейта макета.
     * @param container Контейнер для размещения вида.
     * @param savedInstanceState Сохраненные состояние при создании.
     * @return Корневой вид фрагмента.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleMeetingBinding.inflate(inflater)
        animationSlideRightIn =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_in)
        return binding.root
    }

    /**
     * Выполняется после создания вида фрагмента.
     * Настраивает обработчики кликов для кнопок и получает токен Firebase.
     * @param view Вид фрагмента.
     * @param savedInstanceState Сохраненные состояние при создании.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideRightIn)

        viewModel = ViewModelProvider(requireActivity()).get(ScheduleMeetingViewModel::class.java)

        binding.buttonInvitationMeeting.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_fragment_schedule_meeting_to_fragment_schedule_meeting_invitation)
        }

        binding.buttonViewMeetings.setOnClickListener {
            createTestNotification()
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.d("Token", it.result)
        }
    }

    /**
     * Освобождает ресурсы, связанные с привязкой макета, при уничтожении фрагмента.
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Создает тестовое уведомление.
     * Проверяет разрешение на отправку уведомлений и запрашивает его, если необходимо.
     * Создает уведомление и отображает его.
     */
    private fun createTestNotification() {

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Если разрешение не предоставлено, запросите его у пользователя
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_PERMISSION_REQUEST_CODE)
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

        val notification = NotificationCompat.Builder(requireContext(), MainActivity.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.button_ic_schedule_meeting)
            .setContentTitle("Test message from BoardGamerDiary")
            .setContentText("Это тестовое сообщение из приложения \"Дневник настольщика\"")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
    }

    /**
     * Обрабатывает результат запроса разрешений.
     * Если разрешение на отправку уведомлений предоставлено, создается уведомление.
     * Если нет, отображается сообщение об отказе.
     * @param requestCode Код запроса разрешения.
     * @param permissions Массив разрешений.
     * @param grantResults Массив результатов предоставленных разрешений.
     */
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Разрешение было предоставлено, создайте уведомление снова
                createTestNotification()
            } else {
                // Разрешение было отклонено, обработайте это соответствующим образом
                Toast.makeText(requireContext(), "Permission for notifications was denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        private const val NOTIFICATION_ID = 10_000
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
    }
}