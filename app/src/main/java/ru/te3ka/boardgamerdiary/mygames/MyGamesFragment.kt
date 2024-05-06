package ru.te3ka.boardgamerdiary.mygames

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentMyGamesBinding

class MyGamesFragment : Fragment() {
    private var _binding: FragmentMyGamesBinding? = null
    private val binding get() = _binding!!
    private lateinit var animationSlideRightIn: Animation

    private val viewModel: MyGamesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyGamesBinding.inflate(inflater)
        animationSlideRightIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_in)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideRightIn)

        binding.viewPagerMyGames.adapter = MyGamesPagerAdapter(childFragmentManager)
        binding.tableLayoutMyGames.setupWithViewPager(binding.viewPagerMyGames)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}