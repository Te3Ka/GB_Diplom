package ru.te3ka.boardgamerdiary.mygames.wanttoplay

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentWantToPlayBinding
import ru.te3ka.boardgamerdiary.databinding.FragmentWhishListBinding
import ru.te3ka.boardgamerdiary.mygames.wishlist.WishlistAdapter

class WantToPlayFragment : Fragment() {
    private var _binding: FragmentWantToPlayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WantToPlayViewModel by viewModels()

    private val dataList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWantToPlayBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView = binding.recyclerViewWantToPlayList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val recycleAdapter = WantToPlayListAdapter(dataList, requireContext())
        recyclerView.adapter = recycleAdapter

        binding.buttonAddGameInWantToPlaylist.setOnClickListener {
            dataList.add("")
            binding.recyclerViewWantToPlayList.adapter?.notifyItemInserted(dataList.size - 1)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}