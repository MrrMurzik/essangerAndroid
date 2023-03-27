package com.example.messengerAndroid.ui.signUp

import android.os.Bundle
import android.view.View
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.databinding.FragmentSignUpBinding
import com.example.messengerAndroid.extensions.navigate
import com.example.messengerAndroid.foundation.base.BaseFragment
import com.example.messengerAndroid.Constants.EMAIL_DIVIDER_PATTERN
import com.example.messengerAndroid.foundation.utils.Validator


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (SharedPreferencesHelper.getName().isNotEmpty()) {
            navigate().showViewPager()
        }

        setListeners()
    }



    private fun setListeners() {
        binding.buttonRegister.setOnClickListener { registerNewUser() }
    }

    private fun registerNewUser() {
        val email = binding.inputLayoutEmail.editText?.text.toString()
        val password = binding.inputLayoutPassword.editText?.text.toString()

        if (Validator.getValidityEmail(email) && Validator.getValidityPassword(password)) {
            val name = getName(email)
            if (binding.checkBoxRememberMe.isChecked) {
                SharedPreferencesHelper.setName(name)
            }
            navigate().showViewPager()
        } else {
            showError(email, password)
        }
    }

    private fun showError(email: String, password: String) {
        if (!Validator.getValidityEmail(email)) {
           binding.inputLayoutEmail.error = getString(R.string.invalid_email)
        }
        if (!Validator.getValidityPassword(password)) {
            binding.inputLayoutPassword.error = getString(R.string.invalid_password)
        }
    }


    private fun getName(email: String): String {
        val partOfName = email.substring(0, email.indexOf('@'))
        return partOfName.replace(EMAIL_DIVIDER_PATTERN.toRegex(), " ")
    }


}