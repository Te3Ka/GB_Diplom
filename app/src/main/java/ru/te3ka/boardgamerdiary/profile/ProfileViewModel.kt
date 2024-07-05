package ru.te3ka.boardgamerdiary.profile

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.db.BgdDatabase
import ru.te3ka.boardgamerdiary.fcmservice.FcmService
import ru.te3ka.boardgamerdiary.model.Profile
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkProfile
import ru.te3ka.boardgamerdiary.repository.ProfileRepository
import ru.te3ka.boardgamerdiary.service.RetrofitClient
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProfileRepository
    private val _userProfile = MutableLiveData<Profile?>()
    val userProfile: LiveData<Profile?> get() = _userProfile

    private val _photoUri = MutableLiveData<Uri?>()
    val photoUri: LiveData<Uri?> get() = _photoUri

    init {
        val profileDao = BgdDatabase.getDatabase(application).profileDao()
        repository = ProfileRepository(
            profileDao
        )
        viewModelScope.launch {
            repository.profile.collect { profile ->
                _userProfile.value = profile
            }
        }
    }

    fun insert(profile: Profile) = viewModelScope.launch {
        repository.insert(profile)
    }

    fun update(profile: Profile) = viewModelScope.launch {
        repository.update(profile)
    }

    fun delete(profile: Profile) = viewModelScope.launch {
        repository.delete(profile)
    }

    fun navigateToMainMenu(profileFragment: ProfileFragment) {
        profileFragment.findNavController()
            .navigate(R.id.action_fragment_profile_to_fragment_main_menu)
    }

    fun showToastHelpEditField(requireContext: Context) {
        Toast.makeText(requireContext, R.string.click_any_edit_text_field, Toast.LENGTH_SHORT)
            .show()
    }

    fun showToast(context: Context) {
        Toast.makeText(context, "Wow!", Toast.LENGTH_SHORT).show()
    }

    fun updatePhotoUri(uri: Uri) {
        _photoUri.value = uri
    }

    fun saveProfile(
        contactId: Int?,
        myCollectionId: Int?,
        wishlistId: Int?,
        wantToPlayId: Int?,
        nickname: String,
        firstName: String,
        surname: String,
        city: String,
        contactPhone: String,
        email: String,
        hobbies: String,
        dayOfBirth: Int,
        monthOfBirth: Int,
        yearOfBirth: Int,
        photoPath: String
    ) {
        viewModelScope.launch {
            val updateProfile = Profile(
                contactId = contactId,
                myCollectionId = myCollectionId,
                wishlistId = wishlistId,
                wantToPlayId = wantToPlayId,
                nickname = nickname,
                firstName = firstName,
                surname = surname,
                city = city,
                contactPhone = contactPhone,
                email = email,
                hobbies = hobbies,
                dayOfBirth = dayOfBirth,
                monthOfBirth = monthOfBirth,
                yearOfBirth = yearOfBirth,
                photoPath = photoPath
            )
            insert(updateProfile)
            uploadProfile(convertToNetworkProfile(updateProfile))
        }
    }

    private fun convertToNetworkProfile(profile: Profile): NetworkProfile {
        return NetworkProfile(
            contactPhone = profile.contactPhone,
            contactId = profile.contactId,
            myCollectionId = profile.myCollectionId,
            wantToPlayId = profile.wantToPlayId,
            wishlistId = profile.wishlistId,
            nickname = profile.nickname,
            firstName = profile.firstName,
            surname = profile.surname,
            city = profile.city,
            email = profile.email,
            hobbies = profile.hobbies,
            dayOfBirth = profile.dayOfBirth,
            monthOfBirth = profile.monthOfBirth,
            yearOfBirth = profile.yearOfBirth,
            photoPath = profile.photoPath
        )
    }

    fun uploadProfile(networkProfile: NetworkProfile) {
        Log.i(TAG, "Start push profile")
        val requestBody =
            Gson().toJson(networkProfile).toRequestBody("application/json; charset=UTF-8".toMediaTypeOrNull())
        println(requestBody.toString())
        val request = Request.Builder()
            .url("http://192.168.31.193:8080/upload/profile/")// TODO: Тут должен быть сервер
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful)
                    Log.i(TAG, "Send successful")
            }
        })
    }

    fun createImageFile(context: Context): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}