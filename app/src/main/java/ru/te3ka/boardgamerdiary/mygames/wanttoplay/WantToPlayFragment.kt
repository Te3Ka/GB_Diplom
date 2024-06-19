package ru.te3ka.boardgamerdiary.mygames.wanttoplay

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentWantToPlayBinding
import ru.te3ka.boardgamerdiary.databinding.FragmentWhishListBinding
import ru.te3ka.boardgamerdiary.mygames.wishlist.WishlistAdapter

class WantToPlayFragment : Fragment() {
    private var _binding: FragmentWantToPlayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WantToPlayViewModel by viewModels()

    private lateinit var wantToPlayAdapter: WantToPlayListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWantToPlayBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        setupObserver()

        binding.buttonAddGameInWantToPlaylist.setOnClickListener {
            addNewItem()
        }
        return view
    }

    private fun setupRecyclerView() {
        wantToPlayAdapter = WantToPlayListAdapter(emptyList(), { boardgame ->
            viewModel.updateWantToPlay(boardgame)
        }, { boardgame ->
            viewModel.deleteWantToPlay(boardgame)
        })

        binding.recyclerViewWantToPlayList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wantToPlayAdapter
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.allWantToPlay.collect { boardgames ->
                wantToPlayAdapter.updateData(boardgames)
            }
        }
    }

    private fun addNewItem() {
        val newItem = ""
        viewModel.addWantToPlay(newItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}