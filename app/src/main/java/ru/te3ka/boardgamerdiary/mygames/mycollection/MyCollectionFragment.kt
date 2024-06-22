package ru.te3ka.boardgamerdiary.mygames.mycollection

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentMyCollectionBinding
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.model.MyCollection

class MyCollectionFragment : Fragment() {
    private var _binding: FragmentMyCollectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyCollectionViewModel by viewModels()
    private val dataListMyCollection: MutableList<MyCollection> = mutableListOf()
    private lateinit var recyclerAdapterMyCollection: MyCollectionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCollectionBinding.inflate(inflater)

        setupRecyclerView()
        setupObserver()

        binding.buttonAddGameInCollection.setOnClickListener {
            addNewDefaultValueGame()
        }
        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerAdapterMyCollection = MyCollectionListAdapter(mutableListOf(), { boardgame ->
            viewModel.addMyCollection(boardgame)
        }, { boardgame ->
            viewModel.updateMyCollection(boardgame)
        }, { boardgame ->
            viewModel.deleteMyCollection(boardgame)
        })

        binding.recyclerViewMyCollectionList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapterMyCollection
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.allMyCollection.collect { boardgames ->
                recyclerAdapterMyCollection.updateData(boardgames.toMutableList())
            }
        }
    }

    private fun addNewDefaultValueGame() {
        val newGameInCollection = MyCollection(
            name = "",
            score = "",
            numberOfGames = "",
            yearOfPurchase = "",
            monthOfPurchase = ""
        )
        viewModel.addMyCollection(
            newGameInCollection.name,
            newGameInCollection.score,
            newGameInCollection.numberOfGames,
            newGameInCollection.yearOfPurchase,
            newGameInCollection.monthOfPurchase
        )
        Toast.makeText(requireContext(),
            "Game in collection: ${newGameInCollection.id}",
            Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteDialog(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(requireContext().getString(R.string.remove_element_from_my_collectoin))
            .setMessage(requireContext().getString(R.string.are_you_sure))
            .setPositiveButton(requireContext().getString(R.string.i_am_sure)) { dialog, which ->
                dataListMyCollection.removeAt(position)
                binding.recyclerViewMyCollectionList.adapter?.notifyItemRemoved(position)
            }
            .setNegativeButton(requireContext().getString(R.string.i_changed), null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}