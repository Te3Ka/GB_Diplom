package ru.te3ka.boardgamerdiary.statistics

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentStatisticsBinding

/**
 * Фрагмент для отображения статистики.
 * Инфраструктура для работы с UI и анимацией.
 */
class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var animationSlideRightIn: Animation

    /**
     * Создание и настройка представления фрагмента.
     * Здесь происходит создание привязки и загрузка анимации.
     *
     * @param inflater LayoutInflater для создания представления
     * @param container Контейнер, в который будет добавлено представление
     * @param savedInstanceState Сохраненные состояния фрагмента
     * @return Корневое представление фрагмента
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater)
        animationSlideRightIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_in)
        return binding.root
    }

    /**
     * Выполняется после создания представления.
     * Здесь запускается анимация для представления.
     *
     * @param view Созданное представление фрагмента
     * @param savedInstanceState Сохраненные состояния фрагмента
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideRightIn)
    }

    /**
     * Очистка ресурсов фрагмента.
     * Сбрасывает привязку для предотвращения утечек памяти.
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}