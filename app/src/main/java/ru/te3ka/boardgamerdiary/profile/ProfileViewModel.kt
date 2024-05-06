package ru.te3ka.boardgamerdiary.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import ru.te3ka.boardgamerdiary.R

class ProfileViewModel : ViewModel() {

    fun navigateToMainMenu(profileFragment: ProfileFragment) {
        profileFragment.findNavController().navigate(R.id.action_fragment_profile_to_fragment_main_menu)
    }

    fun showToastHelpEditField(requireContext: Context) {
        Toast.makeText(requireContext, R.string.click_any_edit_text_field, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context) {
        Toast.makeText(context, "Wow!", Toast.LENGTH_SHORT).show()
    }
}