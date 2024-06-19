package ru.te3ka.boardgamerdiary.mygames.wishlist

import androidx.recyclerview.widget.DiffUtil
import ru.te3ka.boardgamerdiary.model.Wishlist

class WishlistDiffCallback(
    private val oldList: List<Wishlist>,
    private val newList: List<Wishlist>
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