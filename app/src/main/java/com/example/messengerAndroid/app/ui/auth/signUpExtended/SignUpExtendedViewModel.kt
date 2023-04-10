package com.example.messengerAndroid.app.ui.auth.signUpExtended

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.messengerAndroid.app.data.preferences.SharedPreferencesHelper

class SignUpExtendedViewModel : ViewModel() {

    private val _navigationLiveData = MutableLiveData<NavDirections>()
    val navigationLiveData: LiveData<NavDirections> = _navigationLiveData

    fun registerNameAndPhone(name: String, phone: String) {
//        SharedPreferencesHelper.setName(name)
//        SharedPreferencesHelper.setPhone(phone)
    }

    fun updateDirection() {
        _navigationLiveData.value =
            SignUpExtendedFragmentDirections.actionSignUpExtendedFragmentToViewPagerFragment()
    }

    fun registerUserPhoto(photoUri: String) {
//        SharedPreferencesHelper.setPhoto(photoUri)
    }

}