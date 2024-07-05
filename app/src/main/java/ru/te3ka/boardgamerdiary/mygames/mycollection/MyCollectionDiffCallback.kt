package ru.te3ka.boardgamerdiary.mygames.mycollection

import androidx.recyclerview.widget.DiffUtil
import ru.te3ka.boardgamerdiary.model.MyCollection

/**
 * Класс для вычисления различий между двумя списками коллекции игр.
 *
 * @property oldList Список старых элементов коллекции игр.
 * @property newList Список новых элементов коллекции игр.
 */
class MyCollectionDiffCallback(
    private val oldList: List<MyCollection>,
    private val newList: List<MyCollection>
) : DiffUtil.Callback() {
    /**
     * Возвращает размер старого списка.
     *
     * @return Размер старого списка коллекции игр.
     */
    override fun getOldListSize(): Int = oldList.size

    /**
     * Возвращает размер нового списка.
     *
     * @return Размер нового списка коллекции игр.
     */
    override fun getNewListSize(): Int = newList.size

    /**
     * Определяет, являются ли элементы на указанных позициях в старом и новом списках одним и тем же элементом.
     *
     * @param oldItemPosition Позиция элемента в старом списке.
     * @param newItemPosition Позиция элемента в новом списке.
     * @return true, если элементы на указанных позициях идентичны, иначе false.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    /**
     * Определяет, имеют ли элементы на указанных позициях в старом и новом списках одинаковое содержание.
     *
     * @param oldItemPosition Позиция элемента в старом списке.
     * @param newItemPosition Позиция элемента в новом списке.
     * @return true, если содержимое элементов идентично, иначе false.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
