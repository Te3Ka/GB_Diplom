package ru.te3ka.boardgamerdiary.profile

import android.Manifest
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentProfileBinding
import java.io.File
import java.util.Calendar

private const val CAMERA_PERMISSION_REQUEST_CODE = 1001

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var animationSlideRightIn: Animation
    private lateinit var animationSlideLeftOut: Animation
    private lateinit var inputMethodManager: InputMethodManager
    private val profileViewModel: ProfileViewModel by viewModels()
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private var day: Int = 31
    private var month: Int = -1
    private var year: Int = 2000

    private var nickname: String = ""
    private var firstName: String = ""
    private var surname: String = ""
    private var city: String = ""
    private var contactPhoneNumber: String = ""
    private var contactEmail: String = ""
    private var hobbies: String = ""

    private var photoFile: File? = null
    private var photoUri: Uri? = null
    private var photoPath: String? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            profileViewModel.updatePhotoUri(it)
            Glide.with(this).load(uri).into(binding.imageProfile)
            photoPath = getRealPathFromURI(requireContext(), uri)
        }
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
        if (success) {
            profileViewModel.updatePhotoUri(photoUri!!)
            Glide.with(this).load(photoFile).into(binding.imageProfile)
            photoPath = photoFile.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        animationSlideRightIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_in)
        animationSlideLeftOut = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left_out)

        allEditTextFalseFocusable()

        photoPath?.let {
            val file = File(it)
            if (file.exists()) {
                Glide.with(this).load(file).into(binding.imageProfile)
            }
        }

        return binding.root
    }

    private fun allEditTextFalseFocusable() {
        binding.editTextNickname.isFocusable = false
        binding.editTextFirstName.isFocusable = false
        binding.editTextSurname.isFocusable = false
        binding.editTextCity.isFocusable = false
        binding.editTextContactPhoneNumber.isFocusable = false
        binding.editTextContactEmail.isFocusable = false
        binding.editTextHobbies.isFocusable = false
    }

    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
        var filePath: String? = null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            filePath = cursor.getString(columnIndex)
        }
        return filePath
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideRightIn)

        inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.buttonProfileBackToMainMenu.setOnClickListener {
            view.startAnimation(animationSlideLeftOut)
            profileViewModel.navigateToMainMenu(this)
        }

        binding.buttonEditImageProfile.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            }
            val options = arrayOf(
                requireContext().getString(R.string.choose_camera),
                requireContext().getString(R.string.choose_gallery)
            )
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(requireContext().getString(R.string.select_option_profile_image))
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> {
                            photoFile = profileViewModel.createImageFile(requireContext())
                            photoUri = FileProvider.getUriForFile(requireContext(), "ru.te3ka.boardgamerdiary.fileprovider", photoFile!!)
                            takePicture.launch(photoUri)
                        }
                        1 -> getContent.launch("image/*")
                    }
                    dialog.dismiss()
                }
                .show()
        }

        setupEditTextListener(binding.editTextNickname) { profileViewModel.showToastHelpEditField(requireContext()) }
        setupEditTextListener(binding.editTextFirstName) { profileViewModel.showToastHelpEditField(requireContext()) }
        setupEditTextListener(binding.editTextSurname) { profileViewModel.showToastHelpEditField(requireContext()) }
        setupEditTextListener(binding.editTextCity) { profileViewModel.showToastHelpEditField(requireContext()) }
        setupEditTextListener(binding.editTextContactPhoneNumber) { profileViewModel.showToastHelpEditField(requireContext()) }
        setupEditTextListener(binding.editTextContactEmail) { profileViewModel.showToastHelpEditField(requireContext()) }
        setupEditTextListener(binding.editTextHobbies) { profileViewModel.showToastHelpEditField(requireContext()) }

        binding.containerBirthday.setOnLongClickListener {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            if (year == 0 && month == -1 && day == 200) {
                val calendar = Calendar.getInstance()
                year = calendar.get(Calendar.YEAR)
                month = calendar.get(Calendar.MONTH)
                day = calendar.get(Calendar.DAY_OF_MONTH)
            }

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    this.day = dayOfMonth
                    this.month = monthOfYear
                    this.year = year
                    binding.textSelectDayOfBirth.text = dayOfMonth.toString()
                    binding.textSelectYearOfBirth.text = year.toString()
                    binding.textSelectMonthOfBirth.text = when (monthOfYear) {
                        0 -> requireContext().getString(R.string.month_jan)
                        1 -> requireContext().getString(R.string.month_feb)
                        2 -> requireContext().getString(R.string.month_mar)
                        3 -> requireContext().getString(R.string.month_apr)
                        4 -> requireContext().getString(R.string.month_may)
                        5 -> requireContext().getString(R.string.month_jun)
                        6 -> requireContext().getString(R.string.month_jul)
                        7 -> requireContext().getString(R.string.month_aug)
                        8 -> requireContext().getString(R.string.month_sep)
                        9 -> requireContext().getString(R.string.month_oct)
                        10 -> requireContext().getString(R.string.month_nov)
                        11 -> requireContext().getString(R.string.month_dec)
                        else -> requireContext().getString(R.string.month_error)
                    }
                },
                year, month, day
            )
            datePickerDialog.show()
            true
        }

        binding.buttonSaveProfile.setOnClickListener {
            view.startAnimation(animationSlideLeftOut)
            viewModelScope.launch {
                val contactId = profileViewModel.getContactId(contactPhoneNumber)
                val myCollectionId = profileViewModel.getMyCollectionId(contactPhoneNumber)
                val wishlistId = profileViewModel.getWishlistId(contactPhoneNumber)
                val wantToPlayId = profileViewModel.getWantToPlayId(contactPhoneNumber)

                profileViewModel.saveProfile(
                    contactId = contactId,
                    myCollectionId = myCollectionId,
                    wishlistId = wishlistId,
                    wantToPlayId = wantToPlayId,
                    nickname = binding.editTextNickname.text.toString(),
                    firstName = binding.editTextFirstName.text.toString(),
                    surname = binding.editTextSurname.text.toString(),
                    city = binding.editTextCity.text.toString(),
                    contactPhone = binding.editTextContactPhoneNumber.text.toString(),
                    email = binding.editTextContactEmail.text.toString(),
                    hobbies = binding.editTextHobbies.text.toString(),
                    dayOfBirth = binding.textSelectDayOfBirth.text.toString().toInt(),
                    monthOfBirth = month,
                    yearOfBirth = binding.textSelectYearOfBirth.text.toString().toInt(),
                    photoPath = photoPath ?: ""
                )
                profileViewModel.navigateToMainMenu(this@ProfileFragment)
            }
        }

        profileViewModel.getProfile().observe(viewLifecycleOwner) { profile ->
            profile?.let {
                nickname = it.nickname
                firstName = it.firstName
                surname = it.surname
                city = it.city
                contactPhoneNumber = it.contactPhone
                contactEmail = it.email
                hobbies = it.hobbies
                day = it.dayOfBirth
                month = it.monthOfBirth
                year = it.yearOfBirth
                photoPath = it.photoPath

                binding.editTextNickname.setText(it.nickname)
                binding.editTextFirstName.setText(it.firstName)
                binding.editTextSurname.setText(it.surname)
                binding.editTextCity.setText(it.city)
                binding.editTextContactPhoneNumber.setText(it.contactPhone)
                binding.editTextContactEmail.setText(it.email)
                binding.editTextHobbies.setText(it.hobbies)
                binding.textSelectDayOfBirth.text = it.dayOfBirth.toString()
                binding.textSelectYearOfBirth.text = it.yearOfBirth.toString()
                binding.textSelectMonthOfBirth.text = when (it.monthOfBirth) {
                    0 -> requireContext().getString(R.string.month_jan)
                    1 -> requireContext().getString(R.string.month_feb)
                    2 -> requireContext().getString(R.string.month_mar)
                    3 -> requireContext().getString(R.string.month_apr)
                    4 -> requireContext().getString(R.string.month_may)
                    5 -> requireContext().getString(R.string.month_jun)
                    6 -> requireContext().getString(R.string.month_jul)
                    7 -> requireContext().getString(R.string.month_aug)
                    8 -> requireContext().getString(R.string.month_sep)
                    9 -> requireContext().getString(R.string.month_oct)
                    10 -> requireContext().getString(R.string.month_nov)
                    11 -> requireContext().getString(R.string.month_dec)
                    else -> requireContext().getString(R.string.month_error)
                }

                photoPath?.let { path ->
                    val file = File(path)
                    if (file.exists()) {
                        Glide.with(this).load(file).into(binding.imageProfile)
                    }
                }
            }
        }

        binding.buttonEditProfile.setOnClickListener {
            binding.apply {
                editTextNickname.isFocusableInTouchMode = true
                editTextFirstName.isFocusableInTouchMode = true
                editTextSurname.isFocusableInTouchMode = true
                editTextCity.isFocusableInTouchMode = true
                editTextContactPhoneNumber.isFocusableInTouchMode = true
                editTextContactEmail.isFocusableInTouchMode = true
                editTextHobbies.isFocusableInTouchMode = true
            }
        }
    }

    private fun setupEditTextListener(editText: EditText, onClick: (String) -> Unit) {
        editText.setOnClickListener {
            onClick(editText.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}