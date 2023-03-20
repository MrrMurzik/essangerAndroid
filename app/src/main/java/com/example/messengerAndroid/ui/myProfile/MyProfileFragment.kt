package com.example.messengerAndroid.ui.myProfile

import android.os.Bundle
import android.view.View
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.databinding.FragmentMyProfileBinding
import com.example.messengerAndroid.extensions.cropPhoto
import com.example.messengerAndroid.extensions.navigate
import com.example.messengerAndroid.ui.base.BaseFragment

class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserInfo()
        setListeners()
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
