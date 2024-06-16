package ru.te3ka.boardgamerdiary.mygames

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.te3ka.boardgamerdiary.R
import java.util.Calendar

class CustomMyCollectionListAdapter(private val dataList: List<String>, private val context: Context) : RecyclerView.Adapter<CustomMyCollectionListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        val editTextTitle: EditText = itemView.findViewById(R.id.editText_title_game_in_collection)
        val numberPickerMonthPurchase: NumberPicker = itemView.findViewById(R.id.numberPicker_month_purchase)
        val numberPickerMyScore: NumberPicker = itemView.findViewById(R.id.numberPicker_my_score_game_in_collection)

        init {
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {

        }

        override fun onLongClick(view: View?): Boolean {
            listener?.onItemLongClick(adapterPosition)
            return true
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomMyCollectionListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_element_list_my_collection_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = dataList[position]
        holder.editTextTitle.setText(dataItem)

        val months = arrayOf(
            context.getString(R.string.month_jan),
            context.getString(R.string.month_feb),
            context.getString(R.string.month_mar),
            context.getString(R.string.month_apr),
            context.getString(R.string.month_may),
            context.getString(R.string.month_jun),
            context.getString(R.string.month_jul),
            context.getString(R.string.month_aug),
            context.getString(R.string.month_sep),
            context.getString(R.string.month_oct),
            context.getString(R.string.month_nov),
            context.getString(R.string.month_dec)
        )
        holder.numberPickerMonthPurchase.minValue = 0
        holder.numberPickerMonthPurchase.maxValue = months.size - 1
        holder.numberPickerMonthPurchase.displayedValues = months
        holder.numberPickerMonthPurchase.wrapSelectorWheel = false

        holder.numberPickerMyScore.minValue = 0
        holder.numberPickerMyScore.maxValue = 10
        holder.numberPickerMyScore.wrapSelectorWheel = false
    }

    private var listener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
