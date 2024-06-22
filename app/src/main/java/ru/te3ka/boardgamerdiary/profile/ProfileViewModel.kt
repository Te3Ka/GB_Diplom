package ru.te3ka.boardgamerdiary.profile

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.db.BgdDatabase
import ru.te3ka.boardgamerdiary.model.Contact
import ru.te3ka.boardgamerdiary.model.Profile
import ru.te3ka.boardgamerdiary.repository.ProfileRepository
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProfileRepository
    val userProfile: LiveData<Profile?>

    private val _photoUri = MutableLiveData<Uri?>()
    val photoUri: LiveData<Uri?> get() = _photoUri

    init {
        val profileDao = BgdDatabase.getDatabase(application).profileDao()
        repository = ProfileRepository(profileDao)
        userProfile = repository.profile.asLiveData()
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

    fun getProfile(): LiveData<Profile?> {
        val profileLiveData = MutableLiveData<Profile?>()
        viewModelScope.launch {
            val profile = repository.profile.firstOrNull()
            profileLiveData.postValue(profile)
        }
        return profileLiveData
    }

    fun navigateToMainMenu(profileFragment: ProfileFragment) {
        profileFragment.findNavController().navigate(R.id.action_fragment_profile_to_fragment_main_menu)
    }

    fun showToastHelpEditField(requireContext: Context) {
        Toast.makeText(requireContext, R.string.click_any_edit_text_field, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context) {
        Toast.makeText(context, "Wow!", Toast.LENGTH_SHORT).show()
    }

    fun updatePhotoUri(uri: Uri) {
        _photoUri.value = uri
    }

    suspend fun getContactId(contactPhoneNumber: String) : Int {
        return repository.getContactId(contactPhoneNumber)
    }

    suspend fun getMyCollectionId(contactPhoneNumber: String) : Int{
        return repository.getMyCollectionId(contactPhoneNumber)
    }

    suspend fun getWishlistId(contactPhoneNumber: String) : Int {
        return repository.getWishlistId(contactPhoneNumber)
    }

    suspend fun getWantToPlayId(contactPhoneNumber: String) : Int {
        return repository.getWantToPlayId(contactPhoneNumber)
    }

    fun saveProfile(
        contactId: Int,
        myCollectionId: Int,
        wishlistId: Int,
        wantToPlayId: Int,
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
            val profile = Profile(
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
            insert(profile)
        }
    }

    fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}