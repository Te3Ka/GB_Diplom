package ru.te3ka.boardgamerdiary.profile

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentProfileBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
private const val KEY_NICKNAME = "nickname"
private const val KEY_FIRST_NAME = "first_name"
private const val KEY_SURNAME = "surname"
private const val KEY_CITY = "city"
private const val KEY_CONTACT_PHONE = "contact_phone"
private const val KEY_CONTACT_EMAIL = "contact_email"
private const val KEY_HOBBIES = "hobbies"
private const val KEY_DAY = "day"
private const val KEY_MONTH = "month"
private const val KEY_YEAR = "year"
private const val SHARED_VALUE = "shared_value"
private const val KEY_PHOTO_PATH = "photo_path_key"

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var animationSlideRightIn: Animation
    private lateinit var animationSlideLeftOut: Animation
    private lateinit var inputMethodManager: InputMethodManager
    private val viewModel: ProfileViewModel by viewModels()
    private var day: Int = 0
    private var month: Int = -1
    private var year: Int = 0

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

    // Получение полного адреса к фотографии из Галереи.
    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
        var filePath: String? = null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            filePath = cursor.getString(columnIndex)
        }
        return filePath
    }

    // Вставка фотографии из Галереи
    // TODO: сохранение фотографии из Галереи работает немного неправильно.
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Glide.with(this).load(uri).into(binding.imageProfile)
                photoPath = getRealPathFromURI(requireContext(), uri)
            }
        }

    // Вставка фотографии из камеры
    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                Glide.with(this).load(photoFile).into(binding.imageProfile)
                photoPath = photoFile.toString()
            }
        }

    // Сохранение данных
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_NICKNAME, nickname)
        outState.putString(KEY_FIRST_NAME, firstName)
        outState.putString(KEY_SURNAME, surname)
        outState.putString(KEY_CITY, city)
        outState.putString(KEY_CONTACT_PHONE, contactPhoneNumber)
        outState.putString(KEY_CONTACT_EMAIL, contactEmail)
        outState.putString(KEY_HOBBIES, hobbies)
        outState.putInt(KEY_DAY, day)
        outState.putInt(KEY_MONTH, month)
        outState.putInt(KEY_YEAR, year)
        outState.putString(KEY_PHOTO_PATH, photoPath)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Загрузка данных.
        val sharedPreferences = context?.getSharedPreferences(SHARED_VALUE, Context.MODE_PRIVATE)
        nickname = sharedPreferences?.getString(KEY_NICKNAME, "").toString()
        firstName = sharedPreferences?.getString(KEY_FIRST_NAME, "").toString()
        surname = sharedPreferences?.getString(KEY_SURNAME, "").toString()
        city = sharedPreferences?.getString(KEY_CITY, "").toString()
        contactPhoneNumber = sharedPreferences?.getString(KEY_CONTACT_PHONE, "").toString()
        contactEmail = sharedPreferences?.getString(KEY_CONTACT_EMAIL, "").toString()
        hobbies = sharedPreferences?.getString(KEY_HOBBIES, "").toString()

        day = sharedPreferences?.getInt(KEY_DAY, 0) ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        month = sharedPreferences?.getInt(KEY_MONTH, -1) ?: Calendar.getInstance().get(Calendar.MONTH)
        year = sharedPreferences?.getInt(KEY_YEAR, 0) ?: Calendar.getInstance().get(Calendar.YEAR)

        photoPath = sharedPreferences?.getString(KEY_PHOTO_PATH, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        animationSlideRightIn =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_in)
        animationSlideLeftOut =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left_out)
        allEditTextFalseFocusable()

        if (nickname == "null") {
            nickname = ""
        }
        if (firstName == "null") {
            firstName = ""
        }
        if (surname == "null") {
            surname = ""
        }
        if (city == "null") {
            city = ""
        }
        if (contactPhoneNumber == "null") {
            contactPhoneNumber = ""
        }
        if (contactEmail == "null") {
            contactEmail = ""
        }
        if (hobbies == "null") {
            hobbies = ""
        }
        binding.editTextNickname.setText(nickname)
        binding.editTextFirstName.setText(firstName)
        binding.editTextSurname.setText(surname)
        binding.editTextCity.setText(city)
        binding.editTextContactPhoneNumber.setText(contactPhoneNumber)
        binding.editTextContactEmail.setText(contactEmail)
        binding.editTextHobbies.setText(hobbies)

        if (day == 0) {
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        }
        if (month == -1) {
            month = Calendar.getInstance().get(Calendar.MONTH)
        }
        if (year == 0) {
            year = Calendar.getInstance().get(Calendar.YEAR)
        }
        binding.textSelectDayOfBirth.text = day.toString()
        binding.textSelectYearOfBirth.text = year.toString()
        binding.textSelectMonthOfBirth.text = when (month) {
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

    private fun createImageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    // TODO: перенести логику во ViewModel
    // TODO: Переделать слушатели

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideRightIn)

        inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.buttonProfileBackToMainMenu.setOnClickListener {
            view.startAnimation(animationSlideLeftOut)
            viewModel.navigateToMainMenu(this)
        }

        binding.buttonEditImageProfile.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
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
                            photoFile = createImageFile()
                            photoUri = FileProvider.getUriForFile(
                                requireContext(),
                                "ru.te3ka.boardgamerdiary.fileprovider",
                                photoFile!!
                            )
                            takePicture.launch(photoUri)
                        }
                        1 -> getContent.launch("image/*")
                    }
                    dialog.dismiss()
                }
                .show()
        }

        binding.editTextNickname.setOnClickListener {
            if (binding.editTextNickname.length() >= 1
                && binding.editTextNickname.isFocusable
            ) {
                nickname = binding.editTextNickname.text.toString()
                inputMethodManager.hideSoftInputFromWindow(binding.editTextNickname.windowToken, 0)
                binding.editTextNickname.isFocusable = false
            } else
                viewModel.showToastHelpEditField(requireContext())
        }
        binding.editTextNickname.setOnLongClickListener {
            binding.editTextNickname.isFocusable = true
            binding.editTextNickname.isFocusableInTouchMode = true
            binding.editTextNickname.requestFocus()
            inputMethodManager.showSoftInput(
                binding.editTextNickname,
                InputMethodManager.SHOW_IMPLICIT
            )
            true
        }

        binding.editTextFirstName.setOnClickListener {
            if (binding.editTextFirstName.length() >= 1
                && binding.editTextFirstName.isFocusable
            ) {
                firstName = binding.editTextFirstName.text.toString()
                inputMethodManager.hideSoftInputFromWindow(binding.editTextFirstName.windowToken, 0)
                binding.editTextFirstName.isFocusable = false
            } else
                viewModel.showToastHelpEditField(requireContext())
        }
        binding.editTextFirstName.setOnLongClickListener {
            binding.editTextFirstName.isFocusable = true
            binding.editTextFirstName.isFocusableInTouchMode = true
            binding.editTextFirstName.requestFocus()
            inputMethodManager.showSoftInput(
                binding.editTextFirstName,
                InputMethodManager.SHOW_IMPLICIT
            )
            true
        }


        binding.editTextSurname.setOnClickListener {
            if (binding.editTextSurname.length() >= 1
                && binding.editTextSurname.isFocusable
            ) {
                surname = binding.editTextSurname.text.toString()
                inputMethodManager.hideSoftInputFromWindow(binding.editTextSurname.windowToken, 0)
                binding.editTextSurname.isFocusable = false
            } else
                viewModel.showToastHelpEditField(requireContext())
        }
        binding.editTextSurname.setOnLongClickListener {
            binding.editTextSurname.isFocusable = true
            binding.editTextSurname.isFocusableInTouchMode = true
            binding.editTextSurname.requestFocus()
            inputMethodManager.showSoftInput(
                binding.editTextSurname,
                InputMethodManager.SHOW_IMPLICIT
            )
            true
        }

        binding.editTextCity.setOnClickListener {
            if (binding.editTextCity.length() >= 1
                && binding.editTextCity.isFocusable
            ) {
                city = binding.editTextCity.text.toString()
                inputMethodManager.hideSoftInputFromWindow(binding.editTextCity.windowToken, 0)
                binding.editTextCity.isFocusable = false
            } else
                viewModel.showToastHelpEditField(requireContext())
        }
        binding.editTextCity.setOnLongClickListener {
            binding.editTextCity.isFocusable = true
            binding.editTextCity.isFocusableInTouchMode = true
            binding.editTextCity.requestFocus()
            inputMethodManager.showSoftInput(
                binding.editTextCity,
                InputMethodManager.SHOW_IMPLICIT
            )
            true
        }

        binding.editTextContactPhoneNumber.setOnClickListener {
            if (binding.editTextContactPhoneNumber.length() >= 1
                && binding.editTextContactPhoneNumber.isFocusable
            ) {
                contactPhoneNumber = binding.editTextContactPhoneNumber.text.toString()
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextContactPhoneNumber.windowToken,
                    0
                )
                binding.editTextContactPhoneNumber.isFocusable = false
            } else
                viewModel.showToastHelpEditField(requireContext())
        }
        binding.editTextContactPhoneNumber.setOnLongClickListener {
            binding.editTextContactPhoneNumber.isFocusable = true
            binding.editTextContactPhoneNumber.isFocusableInTouchMode = true
            binding.editTextContactPhoneNumber.requestFocus()
            inputMethodManager.showSoftInput(
                binding.editTextContactPhoneNumber,
                InputMethodManager.SHOW_IMPLICIT
            )
            true
        }

        binding.editTextContactEmail.setOnClickListener {
            if (binding.editTextContactEmail.length() >= 1
                && binding.editTextContactEmail.isFocusable
            ) {
                contactEmail = binding.editTextContactEmail.text.toString()
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextContactEmail.windowToken,
                    0
                )
                binding.editTextContactEmail.isFocusable = false
            } else
                viewModel.showToastHelpEditField(requireContext())
        }
        binding.editTextContactEmail.setOnLongClickListener {
            binding.editTextContactEmail.isFocusable = true
            binding.editTextContactEmail.isFocusableInTouchMode = true
            binding.editTextContactEmail.requestFocus()
            inputMethodManager.showSoftInput(
                binding.editTextContactEmail,
                InputMethodManager.SHOW_IMPLICIT
            )
            true
        }

        binding.editTextHobbies.setOnClickListener {
            if (binding.editTextHobbies.length() >= 1
                && binding.editTextHobbies.isFocusable
            ) {
                hobbies = binding.editTextHobbies.text.toString()
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextContactEmail.windowToken,
                    0
                )
                binding.editTextHobbies.isFocusable = false
            } else
                viewModel.showToastHelpEditField(requireContext())
        }
        binding.editTextHobbies.setOnLongClickListener {
            binding.editTextHobbies.isFocusable = true
            binding.editTextHobbies.isFocusableInTouchMode = true
            binding.editTextHobbies.requestFocus()
            inputMethodManager.showSoftInput(
                binding.editTextHobbies,
                InputMethodManager.SHOW_IMPLICIT
            )
            true
        }

        binding.containerBirthday.setOnClickListener {
            viewModel.showToastHelpEditField(requireContext())
        }
        binding.containerBirthday.setOnLongClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(), { view, selectedYear, selectedMonth, selectedDay ->
                    year = selectedYear
                    month = selectedMonth
                    day = selectedDay

                    binding.textSelectDayOfBirth.text = day.toString()
                    binding.textSelectYearOfBirth.text = year.toString()
                    binding.textSelectMonthOfBirth.text = when (month) {
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
                }, year, month, day
            )
            datePickerDialog.show()
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val sharedPref = context?.getSharedPreferences(SHARED_VALUE, Context.MODE_PRIVATE)
        with(sharedPref!!.edit()) {
            putString(KEY_NICKNAME, nickname)
            putString(KEY_FIRST_NAME, firstName)
            putString(KEY_SURNAME, surname)
            putString(KEY_CITY, city)
            putString(KEY_CONTACT_PHONE, contactPhoneNumber)
            putString(KEY_CONTACT_EMAIL, contactEmail)
            putString(KEY_HOBBIES, hobbies)
            putInt(KEY_DAY, day)
            putInt(KEY_MONTH, month)
            putInt(KEY_YEAR, year)
            putString(KEY_PHOTO_PATH, photoPath)
            apply()
            Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.save_profile),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}