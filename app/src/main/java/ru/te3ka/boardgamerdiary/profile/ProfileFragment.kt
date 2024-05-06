package ru.te3ka.boardgamerdiary.profile

import android.app.DatePickerDialog
import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentProfileBinding
import java.util.Calendar

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var animationSlideRightIn: Animation
    private lateinit var inputMethodManager: InputMethodManager
    private val viewModel: ProfileViewModel by viewModels()
    private var year = Calendar.getInstance().get(Calendar.YEAR)
    private var month = Calendar.getInstance().get(Calendar.MONTH)
    private var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        animationSlideRightIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_in)

        allEditTextFalseFocusable()
        return binding.root
    }

    private fun allEditTextFalseFocusable() {
        binding.editTextNickname.isFocusable = false
        binding.editTextFirstName.isFocusable = false
        binding.editTextSurname.isFocusable = false
        binding.editTextCity.isFocusable = false
        binding.editTextContactNumber.isFocusable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideRightIn)

        inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.buttonProfileBackToMainMenu.setOnClickListener {
            viewModel.navigateToMainMenu(this)
        }

        // TODO: перенести логику во ViewModel
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

        binding.editTextContactNumber.setOnClickListener {
            if (binding.editTextContactNumber.length() >= 1
                && binding.editTextContactNumber.isFocusable
            ) {
                binding.editTextContactNumber.text = binding.editTextContactNumber.text
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextContactNumber.windowToken,
                    0
                )
                binding.editTextContactNumber.isFocusable = false
            } else
                viewModel.showToastHelpEditField(requireContext())
        }
        binding.editTextContactNumber.setOnLongClickListener {
            binding.editTextContactNumber.isFocusable = true
            binding.editTextContactNumber.isFocusableInTouchMode = true
            binding.editTextContactNumber.requestFocus()
            inputMethodManager.showSoftInput(
                binding.editTextContactNumber,
                InputMethodManager.SHOW_IMPLICIT
            )
            true
        }

        binding.containerBirthday.setOnClickListener {
            viewModel.showToastHelpEditField(requireContext())
        }
        binding.containerBirthday.setOnLongClickListener {
            val calendar = Calendar.getInstance()


            val datePickerDialog = DatePickerDialog(
                requireContext(), { view, selectedYear, selectedMonth, selectedDay ->
                    year = selectedYear
                    month = selectedMonth
                    day = selectedDay

                    binding.textSelectDayOfBirth.text = day.toString()
                    binding.textSelectMonthOfBirth.text = (month + 1).toString()
                    binding.textSelectYearOfBirth.text = year.toString()
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