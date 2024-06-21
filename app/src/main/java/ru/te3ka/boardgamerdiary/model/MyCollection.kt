package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_collection")
data class MyCollection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val score: String,
    val numberOfGames: String,
    val yearOfPurchase: String,
    val monthOfPurchase: String
)
