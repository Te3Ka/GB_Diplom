package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nickname: String,
    val firstName: String,
    val surname: String,
    val city: String,
    val contactPhone: String,
    val email: String,
    val hobbies: String,
    val dayOfBirth: String,
    val monthOfBirth: String,
    val yearOfBirth: String,
    val photoPath: String
)
