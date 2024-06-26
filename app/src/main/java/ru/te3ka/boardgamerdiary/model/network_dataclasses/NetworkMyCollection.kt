package ru.te3ka.boardgamerdiary.model.network_dataclasses

data class NetworkMyCollection(
    val id: Int = 0,
    val name: String,
    val score: String,
    val numberOfGames: String,
    val yearOfPurchase: String,
    val monthOfPurchase: String
)