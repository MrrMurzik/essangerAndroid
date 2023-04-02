package com.example.messengerAndroid.ui.auth.signUp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.databinding.FragmentSignUpBinding
import com.example.messengerAndroid.foundation.extensions.navigate
import com.example.messengerAndroid.foundation.base.BaseFragment


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {


    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (SharedPreferencesHelper.getName().isNotEmpty())
            navigate().showViewPager()

        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(it)
            }
        }

        viewModel.emailErrorLiveData.observe(viewLifecycleOwner) {
            if (it)
                showErrorEmail()
        }
        viewModel.passwordErrorLiveData.observe(viewLifecycleOwner) {
            if (it)
                showErrorPassword()
        }
    }


    private fun setListeners() {
        binding.buttonRegister.setOnClickListener {


            viewModel.registerEmailAndPassword(
                binding.inputLayoutEmail.editText?.text.toString(),
                binding.inputLayoutPassword.editText?.text.toString(),
                binding.checkBoxRememberMe.isChecked
                )

        }
    }

    private fun showErrorPassword() {
        binding.inputLayoutPassword.error = getString(R.string.invalid_password)
    }

    private fun showErrorEmail() {
        binding.inputLayoutEmail.error = getString(R.string.invalid_email)
    }




}