package ru.te3ka.boardgamerdiary

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import ru.te3ka.boardgamerdiary.databinding.FragmentMainMenuBinding
import ru.te3ka.boardgamerdiary.profile.ProfileFragment

class MainMenuFragment : Fragment() {
    private lateinit var binding: FragmentMainMenuBinding
    private val viewModel: MainMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater)
        return binding.root
    }


    //TODO: Добавить анимации нажатия кнопок
    //TODO: Добавить анимации переходов
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Переход на фрагмент "Профиль"
        binding.buttonProfile.setOnClickListener {
            viewModel.onNavigationButtonClicked(R.id.action_fragment_main_menu_to_fragment_profile)
        }

        // Переход на фрагмент "Мои игры"
        binding.buttonMyGames.setOnClickListener {
            viewModel.onNavigationButtonClicked(R.id.action_fragment_main_menu_to_fragment_my_games)
        }

        // Переход на фрагмент "Назначить встречу"
        binding.buttonScheduleMeeting.setOnClickListener {
            viewModel.onNavigationButtonClicked(R.id.action_fragment_main_menu_to_fragment_schedule_meeting)
        }

        // Переход на фрагмент "Гильдии"
        binding.buttonGuild.setOnClickListener {
            viewModel.onNavigationButtonClicked(R.id.action_fragment_main_menu_to_fragment_guild)
        }

        // Переход на фрагмент "Статистика"
        binding.buttonStatistics.setOnClickListener {
            viewModel.onNavigationButtonClicked(R.id.action_fragment_main_menu_to_fragment_statistics)
        }

        viewModel.navigateToDestination.observe(viewLifecycleOwner, EventObserver {
            destinationId -> findNavController().navigate(destinationId)
        })
    }
}