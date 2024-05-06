package ru.te3ka.boardgamerdiary.profile

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
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

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var animationSlideRightIn: Animation
    private lateinit var animationSlideLeftOut: Animation
    private lateinit var inputMethodManager: InputMethodManager
    private val viewModel: ProfileViewModel by viewModels()
    private var year = Calendar.getInstance().get(Calendar.YEAR)
    private var month = Calendar.getInstance().get(Calendar.MONTH)
    private var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    private var photoFile: File? = null
    private var photoUri: Uri? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Glide.with(this).load(uri).into(binding.imageProfile)
            }
        }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                Glide.with(this).load(photoFile).into(binding.imageProfile)
            }
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
        return binding.root
    }

    private fun allEditTextFalseFocusable() {
        binding.editTextNickname.isFocusable = false
        binding.editTextFirstName.isFocusable = false
        binding.editTextSurname.isFocusable = false
        binding.editTextCity.isFocusable = false
        binding.editTextContactPhoneNumber.isFocusable = false
        binding.editTextContactEmail.isFocusable = false
    }

    private fun createImageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideRightIn)

        inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.buttonProfileBackToMainMenu.setOnClickListener {
            view.startAnimation(animationSlideLeftOut)
            viewModel.navigateToMainMenu(this)
        }

        // TODO: перенести логику во ViewModel
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
                binding.editTextNickname.text = binding.editTextNickname.text
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

        //TODO: Переделать слушатели
        binding.editTextFirstName.setOnClickListener {
            if (binding.editTextFirstName.length() >= 1
                && binding.editTextFirstName.isFocusable
            ) {
                binding.editTextFirstName.text = binding.editTextFirstName.text
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
                binding.editTextSurname.text = binding.editTextSurname.text
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
                binding.editTextCity.text = binding.editTextCity.text
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
                binding.editTextContactPhoneNumber.text = binding.editTextContactPhoneNumber.text
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
                binding.editTextContactEmail.text = binding.editTextContactEmail.text
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}