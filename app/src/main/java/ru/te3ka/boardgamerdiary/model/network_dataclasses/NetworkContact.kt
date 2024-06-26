package ru.te3ka.boardgamerdiary.model.network_dataclasses

data class NetworkContact(
    var id: Int = 0,
    var phone: String,
    var nickname: String,
    var firstName: String,
    var surname: String
)