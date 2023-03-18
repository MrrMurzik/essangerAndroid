package com.example.messengerAndroid.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.databinding.FragmentSignUpBinding
import com.example.messengerAndroid.extensions.navigate
import com.example.messengerAndroid.utils.Constants.EMAIL_DIVIDER_PATTERN
import com.example.messengerAndroid.utils.Validator


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)

        SharedPreferencesHelper.init(context)

        if (SharedPreferencesHelper.getName().isNotEmpty()) {
            navigate().showMyProfileScreen()
        }

        setListeners()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            navigate().showMyProfileScreen()
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