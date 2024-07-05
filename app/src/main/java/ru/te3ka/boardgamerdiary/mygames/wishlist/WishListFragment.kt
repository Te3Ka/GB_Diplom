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

/**
 * Фрагмент для отображения и управления списком желаемых игр.
 *
 * Этот фрагмент использует [WishlistAdapter] для отображения списка желаемых игр и [WishListViewModel]
 * для управления данными списка и их синхронизации с базой данных и сетевым сервером.
 */
class WishListFragment : Fragment() {
    private var _binding: FragmentWhishListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WishListViewModel by viewModels()

    private lateinit var wishlistAdapter: WishlistAdapter

    /**
     * Создает и возвращает корневое представление фрагмента.
     *
     * Здесь происходит установка адаптера для RecyclerView и установка наблюдателя
     * для обновления данных списка.
     *
     * @param inflater LayoutInflater для инфлейта макета фрагмента.
     * @param container Контейнер, в который будет добавлено представление.
     * @param savedInstanceState Сохраненное состояние фрагмента.
     * @return Корневое представление фрагмента.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWhishListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupObserver()

        binding.buttonAddGameInWishlist.setOnClickListener {
            addNewItem()
        }
        return binding.root
    }

    /**
     * Настраивает RecyclerView для отображения списка желаемых игр.
     *
     * Устанавливает адаптер и менеджер компоновки для RecyclerView.
     */
    private fun setupRecyclerView() {
        wishlistAdapter = WishlistAdapter(mutableListOf(), { boardgame ->
            viewModel.updateWishlist(boardgame)
        }, { boardgame ->
            viewModel.deleteWishlist(boardgame)
        })

        binding.recyclerViewWishlist.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wishlistAdapter
        }
    }

    /**
     * Настраивает наблюдатель за данными списка желаемых игр.
     *
     * Слушает изменения в данных и обновляет адаптер при получении новых данных.
     */
    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.allWishlist.collect { boardgames ->
                wishlistAdapter.updateData(boardgames.toMutableList())
            }
        }
    }

    /**
     * Добавляет новый элемент в список желаемых игр.
     *
     * В текущей реализации создается пустой элемент и добавляется в список.
     */
    private fun addNewItem() {
        val newItem = ""
        viewModel.addWishlist(newItem)

    }

    /**
     * Освобождает ресурсы, связанные с привязкой представления, когда фрагмент уничтожается.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}