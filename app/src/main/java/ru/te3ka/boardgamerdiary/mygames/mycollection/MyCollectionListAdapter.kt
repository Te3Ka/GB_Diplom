package ru.te3ka.boardgamerdiary.mygames.mycollection

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.model.MyCollection
import ru.te3ka.boardgamerdiary.model.Wishlist
import ru.te3ka.boardgamerdiary.mygames.wishlist.WishlistDiffCallback

class MyCollectionListAdapter(
    private var dataList: List<MyCollection>,
    private val unAdd: (MyCollection) -> Unit,
    private val unUpdate: (MyCollection) -> Unit,
    private val onDelete: (MyCollection) -> Unit
) :
    RecyclerView.Adapter<MyCollectionListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
        val editTextTitle: EditText = itemView.findViewById(R.id.editText_title_game_in_collection)
        val editTextMonthPurchase: EditText =
            itemView.findViewById(R.id.editText_monthOfGamePurchase)

        //        val spinnerMonthPurchase: Spinner = itemView.findViewById(R.id.spinner_monthOfGamePurchase)
        val editTextYearPurchase: EditText = itemView.findViewById(R.id.editText_yearOfGamePurchase)
        val editTextScores: EditText =
            itemView.findViewById(R.id.editText_my_score_game_in_collection)
        val editTextNumberOfGames: EditText = itemView.findViewById(R.id.editText_number_games)

        init {
            itemView.setOnLongClickListener(this)
//            setupSpinner()
        }

        /*
        private fun setupSpinner() {
            val months = context.resources.getStringArray(R.array.months_array)
            val adapterSpinner = ArrayAdapter(context, android.R.layout.simple_spinner_item, months)
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMonthPurchase.adapter = adapterSpinner
        }
        */

        override fun onLongClick(view: View?): Boolean {
            listener?.onItemLongClick(adapterPosition)
            return true
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_element_list_my_collection_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.editTextTitle.setText(item.name)
        holder.editTextScores.setText(item.score)
        holder.editTextNumberOfGames.setText(item.numberOfGames)
        holder.editTextYearPurchase.setText(item.yearOfPurchase)
        holder.editTextMonthPurchase.setText(item.monthOfPurchase)

        holder.editTextTitle.addTextChangedListener(object : TextWatcher {
            private var job: Job? = null
            private var isUpdating = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return

                job?.cancel()
                job = CoroutineScope(Dispatchers.Main).launch {
                    delay(1500)
                    Log.i("Changed id:", "${item.id}")
                    if (s != null &&
                        s.toString() != item.name &&
                        s.length >= 3) {
                        val updatedCollection = item.copy(name = s.toString())
                        isUpdating = true
                        unUpdate(updatedCollection)
                        isUpdating = false
                    }
                }
            }
        })

        holder.editTextScores.addTextChangedListener(object : TextWatcher {
            private var job: Job? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                job?.cancel()

                job = CoroutineScope(Dispatchers.Main).launch {
                    delay(1500)
                    if (s != null) {
                        val updatedCollection = item.copy(score = s.toString())
                        unUpdate(updatedCollection)
                    }
                }
            }

        })

        holder.editTextNumberOfGames.addTextChangedListener(object : TextWatcher {
            private var job: Job? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                job?.cancel()

                job = CoroutineScope(Dispatchers.Main).launch {
                    delay(1500)
                    if (s != null && s.length >= 3) {
                        val updatedCollection = item.copy(
                            id = item.id,
                            name = s.toString())
                        unUpdate(updatedCollection)
                    }
                }
            }
        })

        /*
        val months = context.resources.getStringArray(R.array.months_array)
        val monthIndex = months.indexOf(dataItem)
        if (monthIndex >= 0) {
            holder.spinnerMonthPurchase.setSelection(monthIndex)
        }
        */

        holder.editTextTitle.setOnLongClickListener {
            onDelete(item)
            Log.i("Delete MyCollection", "${item.id}")
            true
        }
    }

    private var listener: OnItemLongClickListener? = null

    fun addNewItem(item: MyCollection) {
        unAdd(item)
        notifyItemInserted(dataList.size - 1)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newData: List<MyCollection>) {
        val diffCallback = MyCollectionDiffCallback(dataList, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        dataList = newData
        diffResult.dispatchUpdatesTo(this)
    }
}
