package ru.te3ka.boardgamerdiary.contact

import androidx.recyclerview.widget.DiffUtil
import ru.te3ka.boardgamerdiary.model.Contact

/**
 * Класс для вычисления различий между двумя списками [Contact] с помощью DiffUtil.Callback.
 * Используется RecyclerView для эффективного обновления элементов при изменении данных.
 *
 * @property oldList Старый список контактов.
 * @property newList Новый список контактов.
 */
class ContactDiffCallback(
    private val oldList: List<Contact>,
    private val newList: List<Contact>
) : DiffUtil.Callback() {

    /**
     * Возвращает размер старого списка.
     */
    override fun getOldListSize() = oldList.size

    /**
     * Возвращает размер нового списка.
     */
    override fun getNewListSize() = newList.size

    /**
     * Проверяет, являются ли два элемента одним и тем же. Используется для определения, представляют ли
     * два элемента один и тот же контакт.
     *
     * @param oldItemPosition Позиция элемента в старом списке.
     * @param newItemPosition Позиция элемента в новом списке.
     * @return True, если элементы одинаковые, иначе false.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].phone == newList[newItemPosition].phone
    }

    /**
     * Проверяет, одинаково ли содержание двух элементов. Используется для определения, изменились ли
     * детали контакта.
     *
     * @param oldItemPosition Позиция элемента в старом списке.
     * @param newItemPosition Позиция элемента в новом списке.
     * @return True, если содержимое одинаковое, иначе false.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}