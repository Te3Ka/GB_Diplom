package ru.te3ka.boardgamerdiary.model.network_dataclasses

data class NetworkProfile (
    val contactPhone: String,
    val contactId: Int?,
    val myCollectionId: Int?,
    val wantToPlayId: Int?,
    val wishlistId: Int?,
    val nickname: String,
    val firstName: String,
    val surname: String,
    val city: String,
    val email: String,
    val hobbies: String,
    val dayOfBirth: Int,
    val monthOfBirth: Int,
    val yearOfBirth: Int,
    val photoPath: String
)