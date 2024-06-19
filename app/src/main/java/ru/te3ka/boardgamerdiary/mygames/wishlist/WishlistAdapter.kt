package ru.te3ka.boardgamerdiary.mygames.wishlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.te3ka.boardgamerdiary.R

class WishlistAdapter(private val dataList: List<String>, private val context: Context) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

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
        holder.boardgameName.text = item
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}