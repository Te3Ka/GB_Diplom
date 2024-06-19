package ru.te3ka.boardgamerdiary.mygames.wishlist

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.te3ka.boardgamerdiary.databinding.FragmentWhishListBinding

class WishListFragment : Fragment() {
    private var _binding: FragmentWhishListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WishListViewModel by viewModels()

    private val dataList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWhishListBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView = binding.recyclerViewWishlist
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val recycleAdapter = WishlistAdapter(dataList, requireContext())
        recyclerView.adapter = recycleAdapter

        binding.buttonAddGameInWishlist.setOnClickListener {
            dataList.add("")
            binding.recyclerViewWishlist.adapter?.notifyItemInserted(dataList.size - 1)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}