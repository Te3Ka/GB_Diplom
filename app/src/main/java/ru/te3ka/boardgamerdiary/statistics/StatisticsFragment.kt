package ru.te3ka.boardgamerdiary.statistics

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.te3ka.boardgamerdiary.R

class StatisticsFragment : Fragment() {

    companion object {
        fun newInstance() = StatisticsFragment()
    }

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }
}