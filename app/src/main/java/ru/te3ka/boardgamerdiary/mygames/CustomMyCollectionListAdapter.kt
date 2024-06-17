package ru.te3ka.boardgamerdiary.mygames

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import ru.te3ka.boardgamerdiary.R

class CustomMyCollectionListAdapter(private val dataList: List<String>, private val context: Context) : RecyclerView.Adapter<CustomMyCollectionListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        val editTextTitle: EditText = itemView.findViewById(R.id.editText_title_game_in_collection)
//        val editTextMonthPurchase: EditText = itemView.findViewById(R.id.editText_monthOfGamePurchase)
        val spinnerMonthPurchase: Spinner = itemView.findViewById(R.id.spinner_monthOfGamePurchase)
        val editTextYearPurchase: EditText = itemView.findViewById(R.id.editText_yearOfGamePurchase)
        val editTextScores: EditText = itemView.findViewById(R.id.editText_my_score_game_in_collection)

        init {
            itemView.setOnLongClickListener(this)
            setupSpinner()
        }

        private fun setupSpinner() {
            val months = context.resources.getStringArray(R.array.months_array)
            val adapterSpinner = ArrayAdapter(context, android.R.layout.simple_spinner_item, months)
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMonthPurchase.adapter = adapterSpinner
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

        val months = context.resources.getStringArray(R.array.months_array)
        val monthIndex = months.indexOf(dataItem)
        if (monthIndex >= 0) {
            holder.spinnerMonthPurchase.setSelection(monthIndex)
        }
    }

    private var listener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
