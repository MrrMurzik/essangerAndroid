package com.example.messengerAndroid.app.ui.viewPager.myProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.messengerAndroid.R
import com.example.messengerAndroid.app.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.databinding.FragmentMyProfileBinding
import com.example.messengerAndroid.app.foundation.extensions.cropPhoto
import com.example.messengerAndroid.app.foundation.extensions.navigate
import com.example.messengerAndroid.app.foundation.base.BaseFragment
import com.example.messengerAndroid.app.ui.viewPager.ViewPagerFragment

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

        binding.buttonSignOut.setOnClickListener {
            navigate().signOut()
        }

        binding.buttonContacts.setOnClickListener {
            (parentFragment as ViewPagerFragment).viewPager.currentItem = 1
        }
    }


}
