package ru.te3ka.boardgamerdiary.mygames.mycollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentMyCollectionBinding
import ru.te3ka.boardgamerdiary.model.MyCollection

/**
 * Фрагмент, отображающий коллекцию игр пользователя.
 */
class MyCollectionFragment : Fragment() {
    private var _binding: FragmentMyCollectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyCollectionViewModel by viewModels()
    private val dataListMyCollection: MutableList<MyCollection> = mutableListOf()
    private lateinit var recyclerAdapterMyCollection: MyCollectionListAdapter

    /**
     * Создает и возвращает представление для фрагмента.
     *
     * @param inflater LayoutInflater для раздувания макета.
     * @param container Родительский контейнер для макета.
     * @param savedInstanceState Сохраненное состояние, если есть.
     * @return Корневое представление фрагмента.
     */
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

    /**
     * Настраивает RecyclerView для отображения списка игр.
     */
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

    /**
     * Настраивает наблюдателя для обновления данных в RecyclerView.
     */
    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.allMyCollection.collect { boardgames ->
                recyclerAdapterMyCollection.updateData(boardgames.toMutableList())
            }
        }
    }

    /**
     * Добавляет новый элемент в коллекцию игр с умолчательными значениями.
     */
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
    }

    /**
     * Показывает диалог подтверждения удаления элемента из коллекции.
     *
     * @param position Позиция элемента в списке.
     */
    // TODO: Использовать метод удаления.
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

    /**
     * Очищает ссылку на привязку при уничтожении фрагмента.
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}