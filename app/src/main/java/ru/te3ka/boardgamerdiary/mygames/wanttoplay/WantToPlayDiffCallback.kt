package ru.te3ka.boardgamerdiary.mygames.wanttoplay

import androidx.recyclerview.widget.DiffUtil
import ru.te3ka.boardgamerdiary.model.WantToPlay

/**
 * Callback для определения различий между двумя списками объектов WantToPlay.
 * Используется для обновления данных в RecyclerView.
 *
 * @param oldList Старый список объектов WantToPlay.
 * @param newList Новый список объектов WantToPlay.
 */
class WantToPlayDiffCallback(
    private val oldList: List<WantToPlay>,
    private val newList: List<WantToPlay>
) : DiffUtil.Callback() {

    /**
     * Возвращает размер старого списка.
     *
     * @return Размер старого списка.
     */
    override fun getOldListSize() = oldList.size

    /**
     * Возвращает размер нового списка.
     *
     * @return Размер нового списка.
     */
    override fun getNewListSize() = newList.size

    /**
     * Проверяет, являются ли элементы на указанных позициях в старом и новом списках одинаковыми.
     *
     * @param oldItemPosition Позиция элемента в старом списке.
     * @param newItemPosition Позиция элемента в новом списке.
     * @return True, если элементы одинаковые, иначе False.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    /**
     * Проверяет, имеют ли элементы на указанных позициях в старом и новом списках одинаковое содержимое.
     *
     * @param oldItemPosition Позиция элемента в старом списке.
     * @param newItemPosition Позиция элемента в новом списке.
     * @return True, если содержимое элементов одинаковое, иначе False.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}