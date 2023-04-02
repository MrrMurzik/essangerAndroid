package com.example.messengerAndroid.ui.auth.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.messengerAndroid.Constants
import com.example.messengerAndroid.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.foundation.utils.Validator

class SignUpViewModel: ViewModel() {

    private val _navigationLiveData = MutableLiveData<NavDirections>()
    val navigationLiveData: LiveData<NavDirections> = _navigationLiveData

    private val _emailErrorLiveData = MutableLiveData(false)
    val emailErrorLiveData: LiveData<Boolean> = _emailErrorLiveData

    private val _passwordErrorLiveData = MutableLiveData(false)
    val passwordErrorLiveData: LiveData<Boolean> = _passwordErrorLiveData


    fun registerEmailAndPassword(email: String, password: String, isChecked: Boolean) {
        if (checkInputValidity(email, password)) {

            if (isChecked) { saveUser(email, password) }

            _navigationLiveData.value = SignUpFragmentDirections.actionSignUpFragmentToSignUpExtendedFragment()

        }
    }

    private fun saveUser(email: String, password: String) {
        SharedPreferencesHelper.setEmail(email)
        SharedPreferencesHelper.setPassword(password)
    }

    private fun checkInputValidity(email: String, password: String): Boolean {
        var result = true
        if (!Validator.getValidityEmail(email)) {
            _emailErrorLiveData.value = true
            result = false
        }
        if (!Validator.getValidityPassword(password)) {
            _passwordErrorLiveData.value = true
            result = false
        }
        return result
    }


    private fun getName(email: String): String {
        val partOfName = email.substring(0, email.indexOf('@'))
        return partOfName.replace(Constants.EMAIL_DIVIDER_PATTERN.toRegex(), " ")
    }


}