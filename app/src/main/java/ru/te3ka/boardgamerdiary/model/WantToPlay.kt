package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "want_to_play")
data class WantToPlay(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
