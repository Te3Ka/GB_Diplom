package ru.te3ka.boardgamerdiary.mygames.wanttoplay

import androidx.recyclerview.widget.DiffUtil
import ru.te3ka.boardgamerdiary.model.WantToPlay

class WantToPlayDiffCallback(
    private val oldList: List<WantToPlay>,
    private val newList: List<WantToPlay>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}