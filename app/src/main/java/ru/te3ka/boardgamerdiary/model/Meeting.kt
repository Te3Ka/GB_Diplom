package ru.te3ka.boardgamerdiary.model

data class Meeting(
    val date: String,
    val location: String,
    val boardgames: List<String>,
    val contacts: List<String>
)