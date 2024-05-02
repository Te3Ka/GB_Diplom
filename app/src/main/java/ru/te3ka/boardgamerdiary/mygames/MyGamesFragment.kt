package ru.te3ka.boardgamerdiary.mygames

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.te3ka.boardgamerdiary.R

class MyGamesFragment : Fragment() {

    companion object {
        fun newInstance() = MyGamesFragment()
    }

    private val viewModel: MyGamesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my_games, container, false)
    }
}