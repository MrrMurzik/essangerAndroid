package com.example.messengerAndroid.app.ui.auth.signUp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.messengerAndroid.R
import com.example.messengerAndroid.app.data.model.user.UserRepository
import com.example.messengerAndroid.app.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.app.foundation.extensions.navigate
import com.example.messengerAndroid.app.foundation.base.BaseFragment
import com.example.messengerAndroid.app.foundation.extensions.factory
import com.example.messengerAndroid.databinding.FragmentSignUpBinding
import com.example.messengerAndroid.databinding.PartResultBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {


    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("myTag", "")
        if (SharedPreferencesHelper.getName().isNotEmpty())
            navigate().showViewPager()

        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) { result ->
            Log.d("myTag", "${result}")
            val resultBinding = PartResultBinding.bind(binding.root)
            renderResult(
                root = binding.root,
                partRoot = resultBinding.root,
                result = result,
                onPending = {
                    resultBinding.progressBar.visibility = VISIBLE
                },
                onSystemErrorResult = {
                    when (it) {
                        Error.CONNECTION_EXCEPTION -> {
                            resultBinding.textViewError.text = getString(R.string.connection_error)

                        }
                        Error.PARSE_BACKEND_EXCEPTION -> {
                            resultBinding.textViewError.text = getString(R.string.backend_error)
                        }
                        Error.BACKEND_EXCEPTION -> {
                            resultBinding.textViewError.text = getString(R.string.backend_error)

                        }
                        else -> throw IllegalStateException()
                    }
                    resultBinding.errorContainer.visibility = VISIBLE
                    resultBinding.textViewError.visibility = VISIBLE
                    resultBinding.buttonError.visibility = VISIBLE
                },
                onActionErrorResult = {error ->
                    binding.root.children.forEach { it.visibility = VISIBLE }
                    when (error) {
                        Error.INVALID_PASSWORD -> {
                            binding.inputLayoutPassword.error = getString(R.string.invalid_password)
                        }
                        Error.INVALID_EMAIL -> {
                            binding.inputLayoutPassword.error = getString(R.string.invalid_email)
                        }
                        Error.ACCOUNT_ALREADY_EXIST -> {
                            binding.inputLayoutPassword.error = getString(R.string.email_already_exists)
                        }
                        else -> throw IllegalStateException()
                    }
                },
                onSuccess = {navDirection ->
                    binding.root.children.forEach { it.visibility = VISIBLE }
                    findNavController().navigate(navDirection)
                }
            )
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



}