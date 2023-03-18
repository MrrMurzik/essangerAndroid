package com.example.messengerAndroid.ui.myProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.databinding.FragmentMyProfileBinding
import com.example.messengerAndroid.extensions.cropPhoto
import com.example.messengerAndroid.extensions.navigate

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProfileBinding.inflate(layoutInflater)

        setUserInfo()
        setListeners()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUserInfo() {
        with(binding) {
            imageViewPicture.cropPhoto(R.drawable.babyyoda)
            textViewName.text = SharedPreferencesHelper.getName()
        }
    }

    private fun setListeners() {
        binding.buttonContacts.setOnClickListener {
            val isFetched = binding.checkBoxFetchContacts?.isChecked ?: false
            navigate().showMyContactsScreen(isFetched)
        }

        binding.buttonSignOut.setOnClickListener {
            navigate().signOut()
        }

    }

}
