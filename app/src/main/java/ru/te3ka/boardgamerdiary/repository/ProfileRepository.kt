package ru.te3ka.boardgamerdiary.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import ru.te3ka.boardgamerdiary.dao.ProfileDao
import ru.te3ka.boardgamerdiary.model.Profile

class ProfileRepository(private val profileDao: ProfileDao) {
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
}