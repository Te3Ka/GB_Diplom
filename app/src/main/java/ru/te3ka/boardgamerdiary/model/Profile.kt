package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(entity = Contact::class, parentColumns = ["id"], childColumns = ["contactId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = MyCollection::class, parentColumns = ["id"], childColumns = ["myCollectionId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Wishlist::class, parentColumns = ["id"], childColumns = ["wishlistId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = WantToPlay::class, parentColumns = ["id"], childColumns = ["wantToPlayId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Profile(
    @PrimaryKey(autoGenerate = false) val contactPhone: String,
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
