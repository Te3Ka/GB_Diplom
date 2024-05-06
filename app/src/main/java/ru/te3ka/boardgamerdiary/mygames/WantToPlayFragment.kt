package ru.te3ka.boardgamerdiary.mygames

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentWantToPlayBinding

class WantToPlayFragment : Fragment() {
    private var _binding: FragmentWantToPlayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WantToPlayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_want_to_play, container, false)
    }
}