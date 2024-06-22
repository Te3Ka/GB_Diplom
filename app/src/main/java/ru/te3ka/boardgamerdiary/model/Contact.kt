package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var phone: String,
    var nickname: String,
    var firstName: String,
    var surname: String
)
