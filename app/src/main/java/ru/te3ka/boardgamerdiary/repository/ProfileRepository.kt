package ru.te3ka.boardgamerdiary.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import ru.te3ka.boardgamerdiary.dao.ContactDao
import ru.te3ka.boardgamerdiary.dao.MyCollectionDao
import ru.te3ka.boardgamerdiary.dao.ProfileDao
import ru.te3ka.boardgamerdiary.dao.WantToPlayDao
import ru.te3ka.boardgamerdiary.dao.WishlistDao
import ru.te3ka.boardgamerdiary.model.Contact
import ru.te3ka.boardgamerdiary.model.MyCollection
import ru.te3ka.boardgamerdiary.model.Profile
import ru.te3ka.boardgamerdiary.model.WantToPlay
import ru.te3ka.boardgamerdiary.model.Wishlist

class ProfileRepository(
    private val profileDao: ProfileDao
) {
    val profile: Flow<Profile?> = profileDao.getProfile()

    suspend fun insert(profile: Profile) {
        profileDao.insertProfile(profile)
    }

    suspend fun update(profile: Profile) {
        profileDao.updateProfile(profile)
    }

    suspend fun delete(profile: Profile) {
        profileDao.deleteProfile(profile)
    }

    suspend fun getContactId(contactPhoneNumber: String) : Int {
        return profileDao.getContactId(contactPhoneNumber) ?: 0
    }

    suspend fun getMyCollectionId(contactPhoneNumber: String) : Int {
        return profileDao.getMyCollectionId(contactPhoneNumber) ?: 0
    }

    suspend fun getWishlistId(contactPhoneNumber: String) : Int {
        return profileDao.getWishListId(contactPhoneNumber) ?: 0
    }

    suspend fun getWantToPlayId(contactPhoneNumber: String) : Int {
        return profileDao.getWantToPlayId(contactPhoneNumber) ?: 0
    }

    suspend fun insertContact(contact: Contact) {
        profileDao.insertContact(contact)
    }

    suspend fun insertMyCollection(myCollection: MyCollection) {
        profileDao.insertMyCollection(myCollection)
    }

    suspend fun insertWishlist(wishlist: Wishlist) {
        profileDao.insertWishlist(wishlist)
    }

    suspend fun insertWantToPlay(wantToPlay: WantToPlay) {
        profileDao.insertWantToPlay(wantToPlay)
    }
}