package ru.te3ka.boardgamerdiary.mygames.wanttoplay

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.model.WantToPlay
import ru.te3ka.boardgamerdiary.model.Wishlist
import ru.te3ka.boardgamerdiary.mygames.wishlist.WishlistAdapter
import ru.te3ka.boardgamerdiary.mygames.wishlist.WishlistDiffCallback

class WantToPlayListAdapter(
    private var dataList: List<WantToPlay>,
    private val unUpdate: (WantToPlay) -> Unit,
    private val onDelete: (WantToPlay) -> Unit
    ) :
    RecyclerView.Adapter<WantToPlayListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boardgameName: TextView = itemView.findViewById(R.id.editText_wishlist_boardgameName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_element_list_wishlist_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.boardgameName.text = item.name

        holder.boardgameName.addTextChangedListener(object : TextWatcher {
            private var job: Job? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                job?.cancel()

                job = CoroutineScope(Dispatchers.Main).launch {
                    delay(3000)
                    if (s != null && s.length >= 3) {
                        val updatedWishlist = item.copy(name = s.toString())
                        unUpdate(updatedWishlist)
                    }
                }
            }
        })

        holder.boardgameName.setOnLongClickListener {
            onDelete(item)
            true
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newData: List<WantToPlay>) {
        val diffCallback = WantToPlayDiffCallback(dataList, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        dataList = newData
        diffResult.dispatchUpdatesTo(this)
    }
}