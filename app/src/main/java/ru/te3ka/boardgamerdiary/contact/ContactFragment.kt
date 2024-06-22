package ru.te3ka.boardgamerdiary.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.databinding.FragmentContactBinding
import ru.te3ka.boardgamerdiary.db.BgdDatabase
import ru.te3ka.boardgamerdiary.model.Contact
import ru.te3ka.boardgamerdiary.repository.ContactRepository

class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var animationSlideRightIn: Animation

    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater)
        animationSlideRightIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_in)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startAnimation(animationSlideRightIn)

        val database = BgdDatabase.getDatabase(requireContext())
        val repository = ContactRepository(database.contactDao())
        val viewModelFactory = ContactViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ContactViewModel::class.java)

        val recyclerView = binding.recyclerViewContacts
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val recycleAdapter = ContactListAdapter(mutableListOf(), viewModel)
        recyclerView.adapter = recycleAdapter

        viewModel.allContacts.observe(viewLifecycleOwner, Observer { contacts ->
            recycleAdapter.updateContacts(contacts)
        })

        binding.buttonAddNewContact.setOnClickListener {
            addNewContact()
        }
    }

    private fun addNewContact() {
        viewModel.allContacts.value?.let { contacts ->
            val count = contacts.size
            val phone = "+" + (7999L * 1000_00_00L + count).toString()
            val contact = Contact(phone = phone, nickname = "Lost", firstName = "John", surname = "Doe")
            viewModel.addContact(contact)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}