package ru.te3ka.boardgamerdiary.mygames.wanttoplay

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
import ru.te3ka.boardgamerdiary.model.WantToPlay

/**
 * Адаптер для отображения списка желаемых игр в RecyclerView.
 */
class WantToPlayListAdapter(
    private var wantToPlays: MutableList<WantToPlay>,
    private val unUpdate: (WantToPlay) -> Unit,
    private val onDelete: (WantToPlay) -> Unit
    ) :
    RecyclerView.Adapter<WantToPlayListAdapter.ViewHolder>() {

    /**
     * ViewHolder для элемента списка желаемых игр.
     *
     * @property boardgameName Текстовое поле для отображения названия игры
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boardgameName: TextView = itemView.findViewById(R.id.editText_wishlist_boardgameName)
    }

    /**
     * Создает новый ViewHolder при необходимости.
     *
     * @param parent Родительский ViewGroup
     * @param viewType Тип представления (в данном случае не используется)
     * @return Новый ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_element_list_wishlist_layout, parent, false)
        return ViewHolder(itemView)
    }

    /**
     * Привязывает данные к элементу списка.
     *
     * @param holder ViewHolder, который должен отображать данные
     * @param position Позиция элемента в списке
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wantToPlays[position]
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
                    if (position != RecyclerView.NO_POSITION && position < wantToPlays.size) {
                        val wantToPlay = wantToPlays[position]
                        val updatedWantToLpay = when ("name") {
                            "name" -> wantToPlay.copy(name = s?.toString() ?: "")
                            else -> wantToPlay
                        }
                        unUpdate(updatedWantToLpay)
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
        return wantToPlays.size
    }

    /**
     * Обновляет данные в адаптере.
     *
     * @param newData Новый список данных
     */
    fun updateData(newData: MutableList<WantToPlay>) {
        val diffCallback = WantToPlayDiffCallback(wantToPlays, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        wantToPlays = newData
        diffResult.dispatchUpdatesTo(this)
    }
}