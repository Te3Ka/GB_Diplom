package ru.te3ka.boardgamerdiary.mygames.wanttoplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.databinding.FragmentWantToPlayBinding

/**
 * Фрагмент для отображения списка желаемых игр.
 */
class WantToPlayFragment : Fragment() {
    private var _binding: FragmentWantToPlayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WantToPlayViewModel by viewModels()

    private lateinit var wantToPlayAdapter: WantToPlayListAdapter

    /**
     * Создает и возвращает представление фрагмента.
     *
     * @param inflater LayoutInflater для инфлейта разметки
     * @param container Контейнер, в который будет помещено представление
     * @param savedInstanceState Сохраненное состояние фрагмента
     * @return Корневое представление фрагмента
     */
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

    /**
     * Настройка RecyclerView для отображения списка желаемых игр.
     */
    private fun setupRecyclerView() {
        wantToPlayAdapter = WantToPlayListAdapter(mutableListOf(), { boardgame ->
            viewModel.updateWantToPlay(boardgame)
        }, { boardgame ->
            viewModel.deleteWantToPlay(boardgame)
        })

        binding.recyclerViewWantToPlayList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wantToPlayAdapter
        }
    }

    /**
     * Настройка наблюдателя для обновления данных в RecyclerView.
     */
    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.allWantToPlay.collect { boardgames ->
                wantToPlayAdapter.updateData(boardgames.toMutableList())
            }
        }
    }

    /**
     * Добавление новой игры в список желаемых.
     */
    private fun addNewItem() {
        val newItem = ""
        viewModel.addWantToPlay(newItem)
    }

    /**
     * Освобождение ресурсов при уничтожении представления фрагмента.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}