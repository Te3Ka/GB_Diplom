package ru.te3ka.boardgamerdiary.mygames.wishlist

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.databinding.FragmentWhishListBinding

class WishListFragment : Fragment() {
    private var _binding: FragmentWhishListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WishListViewModel by viewModels()

    private lateinit var wishlistAdapter: WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWhishListBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        setupObserver()

        binding.buttonAddGameInWishlist.setOnClickListener {
            addNewItem()
        }
        return view
    }

    private fun setupRecyclerView() {
        wishlistAdapter = WishlistAdapter(emptyList(), { boardgame ->
            viewModel.updateWishlist(boardgame)
        }, { boardgame ->
            viewModel.deleteWishlist(boardgame)
        })

        binding.recyclerViewWishlist.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wishlistAdapter
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.allWishlist.collect { boardgames ->
                wishlistAdapter.updateData(boardgames)
            }
        }
    }

    private fun addNewItem() {
        val newItem = ""
        viewModel.addWishlist(newItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}