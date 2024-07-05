package ru.te3ka.boardgamerdiary.mygames.wishlist

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.model.Wishlist

/**
 * Адаптер для отображения списка желаемых игр в RecyclerView.
 *
 * @property wishlists Список желаемых игр
 * @property unUpdate Функция обратного вызова для обновления информации о желаемой игре
 * @property onDelete Функция обратного вызова для удаления желаемой игры
 */
class WishlistAdapter(
    private var wishlists: MutableList<Wishlist>,
    private val unUpdate: (Wishlist) -> Unit,
    private val onDelete: (Wishlist) -> Unit
) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    /**
     * ViewHolder для отображения отдельного элемента в списке.
     *
     * @param itemView Вид элемента списка
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boardgameName: TextView = itemView.findViewById(R.id.editText_wishlist_boardgameName)
    }

    /**
     * Создает новый экземпляр ViewHolder и связывает его с макетом элемента списка.
     *
     * @param parent Родительский ViewGroup, в который будет добавлен элемент
     * @param viewType Тип вида (используется для разных макетов, если необходимо)
     * @return Новый экземпляр ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_element_list_wishlist_layout, parent, false)
        return ViewHolder(itemView)
    }

    /**
     * Привязывает данные к элементу списка, используя ViewHolder.
     *
     * @param holder ViewHolder, в который будут установлены данные
     * @param position Позиция элемента в списке
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wishlists[position]
        holder.boardgameName.text = item.name

        holder.boardgameName.addTextChangedListener(object : TextWatcher {
            private val handler = Handler(Looper.getMainLooper())
            private var runnable: Runnable? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                runnable?.let { handler.removeCallbacks(it) }
                runnable = Runnable {
                    val position = holder.adapterPosition
                    if (position != RecyclerView.NO_POSITION && position < wishlists.size) {
                        val wishlist = wishlists[position]
                        val updatedWishList = when ("name") {
                            "name" -> wishlist.copy(name = s?.toString() ?: "")
                            else -> wishlist
                        }
                        unUpdate(updatedWishList)
                    }
                }
                handler.postDelayed(runnable!!, 1500)
            }
        })

        holder.boardgameName.setOnLongClickListener {
            onDelete(item)
            true
        }
    }

    /**
     * Возвращает количество элементов в списке.
     *
     * @return Количество элементов
     */
    override fun getItemCount(): Int {
        return wishlists.size
    }

    /**
     * Обновляет данные в адаптере и уведомляет о изменениях.
     *
     * @param newData Новый список данных
     */
    fun updateData(newData: MutableList<Wishlist>) {
        val diffCallback = WishlistDiffCallback(wishlists, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        wishlists = newData
        diffResult.dispatchUpdatesTo(this)
    }
}