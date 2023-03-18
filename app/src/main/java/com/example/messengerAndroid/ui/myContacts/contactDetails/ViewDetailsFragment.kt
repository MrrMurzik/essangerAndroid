package com.example.messengerAndroid.ui.myContacts.contactDetails

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.databinding.FragmentViewDetailsBinding
import com.example.messengerAndroid.extensions.addPhoto
import com.example.messengerAndroid.utils.Constants.ARG_USER_KEY

class ViewDetailsFragment : Fragment() {

    private lateinit var binding: FragmentViewDetailsBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_USER_KEY, User::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(ARG_USER_KEY)
            } ?: User("", "", "", "", "", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
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
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(user: User) =
            ViewDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER_KEY, user)
                }
            }
    }
}