package ru.te3ka.boardgamerdiary.mygames.mycollection

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.model.MyCollection

class MyCollectionListAdapter(
    private var myCollections: MutableList<MyCollection>,
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

            editTextTitle.addTextChangedListener(
                createTextWatcher(
                    "name",
                    this
                )
            )
            editTextNumberOfGames.addTextChangedListener(
                createTextWatcher(
                    "numberOfGames",
                    this
                )
            )
            editTextScores.addTextChangedListener(
                createTextWatcher(
                    "score",
                    this
                )
            )
            editTextYearPurchase.addTextChangedListener(
                createTextWatcher(
                    "yearOfPurchase",
                    this
                )
            )
            editTextMonthPurchase.addTextChangedListener(
                createTextWatcher(
                    "MonthOfPurchase",
                    this
                )
            )
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

    private fun createTextWatcher(fieldName: String, holder: ViewHolder): TextWatcher {
        return object : TextWatcher {
            private val handler = Handler(Looper.getMainLooper())
            private var runnable: Runnable? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                runnable?.let { handler.removeCallbacks(it) }
                runnable = Runnable {
                    val position = holder.adapterPosition
                    if (position != RecyclerView.NO_POSITION && position < myCollections.size) {
                        val myCollection = myCollections[position]
                        val updatedMyCollection = when (fieldName) {
                            "name" -> myCollection.copy(name = s?.toString() ?: "")
                            "numberOfGames" -> myCollection.copy(numberOfGames = s?.toString() ?: "")
                            "score" -> myCollection.copy(score = s?.toString() ?: "")
                            "yearsOfPurchase" -> myCollection.copy(yearOfPurchase = s?.toString() ?: "")
                            "monthOfPurchased" -> myCollection.copy(monthOfPurchase = s?.toString() ?: "")
                            else -> myCollection
                        }
                        unUpdate(updatedMyCollection)
                    }
                }
                handler.postDelayed(runnable!!, 1500)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_element_list_my_collection_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = myCollections[position]
        holder.editTextTitle.setText(item.name)
        holder.editTextScores.setText(item.score)
        holder.editTextNumberOfGames.setText(item.numberOfGames)
        holder.editTextYearPurchase.setText(item.yearOfPurchase)
        holder.editTextMonthPurchase.setText(item.monthOfPurchase)

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
        notifyItemInserted(myCollections.size - 1)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return myCollections.size
    }

    fun updateData(newData: MutableList<MyCollection>) {
        val diffCallback = MyCollectionDiffCallback(myCollections, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        myCollections = newData
        diffResult.dispatchUpdatesTo(this)
    }
}
