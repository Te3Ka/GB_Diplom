package ru.te3ka.boardgamerdiary.mygames.mycollection

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentMyCollectionBinding
import androidx.recyclerview.widget.LinearLayoutManager

class MyCollectionFragment : Fragment() {
    private var _binding: FragmentMyCollectionBinding? = null
    private val binding get() = _binding!!

    private val dataList: MutableList<String> = mutableListOf()

    private val viewModel: MyCollectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCollectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerViewMyCollectionList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val recycleAdapter = CustomMyCollectionListAdapter(dataList, requireContext())
        recyclerView.adapter = recycleAdapter

        binding.buttonAddGameInCollection.setOnClickListener {
            addNewDefaultValueGame()
        }

        recycleAdapter.setOnItemLongClickListener(object:
            CustomMyCollectionListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) {
                showDeleteDialog(position)
            }
        })
    }

    private fun addNewDefaultValueGame() {
        dataList.add("")
        binding.recyclerViewMyCollectionList.adapter?.notifyItemInserted(dataList.size - 1)
    }

    private fun showDeleteDialog(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(requireContext().getString(R.string.remove_element_from_my_collectoin))
            .setMessage(requireContext().getString(R.string.are_you_sure))
            .setPositiveButton(requireContext().getString(R.string.i_am_sure)) { dialog, which ->
                dataList.removeAt(position)
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