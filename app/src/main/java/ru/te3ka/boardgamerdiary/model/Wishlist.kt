package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class Wishlist(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
