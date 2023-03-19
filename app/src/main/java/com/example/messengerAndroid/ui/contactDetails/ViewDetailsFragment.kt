package com.example.messengerAndroid.ui.contactDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.databinding.FragmentViewDetailsBinding
import com.example.messengerAndroid.extensions.addPhoto

class ViewDetailsFragment : Fragment() {

    private lateinit var binding: FragmentViewDetailsBinding
    private lateinit var user: User
    private val args: ViewDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = args.user ?: User("", "", "", "", "", "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewDetailsBinding.inflate(layoutInflater)
        setUserInfo()
        setListeners()
        return binding.root
    }

    private fun setUserInfo() {
        with(binding) {
            textViewName.text = user.name
            imageViewPicture.addPhoto(user)
            textViewAddress.text = user.address
            textViewJob.text = user.job
        }
    }

    private fun setListeners() {
        binding.imageButtonArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}