package ru.te3ka.boardgamerdiary.guild

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentGuildBinding
import ru.te3ka.boardgamerdiary.databinding.FragmentProfileBinding

class GuildFragment : Fragment() {
    private var _binding: FragmentGuildBinding? = null
    private val binding get() = _binding!!
    private lateinit var animationSlideRightIn: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuildBinding.inflate(inflater)
        animationSlideRightIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_in)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideRightIn)

        binding.buttonTestCrash.setOnClickListener {
            throw RuntimeException("Test crash Guild")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}