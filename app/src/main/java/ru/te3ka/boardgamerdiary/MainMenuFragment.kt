package ru.te3ka.boardgamerdiary

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {
    private lateinit var binding: FragmentMainMenuBinding
    private val viewModel: MainMenuViewModel by viewModels()
    private lateinit var animationSlideRightOut: Animation
    private lateinit var animationSlideLeftIn: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater)
        animationSlideRightOut = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_out)
        animationSlideLeftIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left_in)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideLeftIn)

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
            destinationId ->
            lifecycleScope.launch {
                delay(200)
                view.startAnimation(animationSlideRightOut)
                findNavController().navigate(destinationId)
            }
        })
    }
}