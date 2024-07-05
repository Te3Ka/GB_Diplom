package ru.te3ka.boardgamerdiary.mygames.wishlist

import androidx.recyclerview.widget.DiffUtil
import ru.te3ka.boardgamerdiary.model.Wishlist

/**
 * [DiffUtil.Callback] для отслеживания изменений в списке элементов wishlist.
 *
 * Этот класс используется для оптимизации обновлений в [RecyclerView],
 * чтобы минимизировать количество операций перерисовки при изменении данных.
 *
 * @property oldList Список старых элементов wishlist.
 * @property newList Список новых элементов wishlist.
 */
class WishlistDiffCallback(
    private val oldList: List<Wishlist>,
    private val newList: List<Wishlist>
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
     * Проверяет, являются ли элементы на указанных позициях идентичными.
     *
     * Используется для определения, если элемент в старом списке соответствует элементу в новом списке.
     *
     * @param oldItemPosition Позиция элемента в старом списке.
     * @param newItemPosition Позиция элемента в новом списке.
     * @return `true`, если элементы идентичны, `false` в противном случае.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    /**
     * Проверяет, имеют ли элементы на указанных позициях одинаковое содержание.
     *
     * Используется для определения, если содержимое элементов в старом списке соответствует содержимому
     * элементов в новом списке, когда элементы идентичны.
     *
     * @param oldItemPosition Позиция элемента в старом списке.
     * @param newItemPosition Позиция элемента в новом списке.
     * @return `true`, если содержимое элементов одинаковое, `false` в противном случае.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}