package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = false) val phone: String,
    val nickname: String,
    val firstName: String,
    val surname: String
)
