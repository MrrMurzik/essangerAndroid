package com.example.messengerAndroid.app.ui.auth.signUpExtended

import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.messengerAndroid.R
import com.example.messengerAndroid.app.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.databinding.FragmentSignUpExtendedBinding
import com.example.messengerAndroid.app.foundation.base.BaseFragment
import com.example.messengerAndroid.app.foundation.extensions.addPhoto
import com.example.messengerAndroid.app.ui.auth.signUp.Error
import com.example.messengerAndroid.databinding.PartResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpExtendedFragment: BaseFragment<FragmentSignUpExtendedBinding>(FragmentSignUpExtendedBinding::inflate) {

    private val viewModel: SignUpExtendedViewModel by viewModels()

    private val pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        binding.imageViewPicture.addPhoto(uri?.toString()?: "")
        viewModel.saveUserPhoto(uri.toString())
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.buttonForward.setOnClickListener {
            viewModel.registerNameAndPhone(
                binding.inputLayoutName.editText?.text.toString(),
                binding.inputLayoutPhone.editText?.text.toString(),
            )
        }

        binding.imageViewAddPhoto.setOnClickListener {
            launchPickPhoto()
        }

        binding.buttonCancel.setOnClickListener {
            SharedPreferencesHelper.clearPrefs()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pickMediaLauncher.unregister()
    }

    private fun setObservers() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) {result ->
            val resultBinding = PartResultBinding.bind(binding.root)
            renderResult(
                root = binding.root,
                result = result,
                onPending = {
                    resultBinding.progressBar.visibility = View.VISIBLE
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
                    resultBinding.errorContainer.visibility = View.VISIBLE
                    resultBinding.textViewError.visibility = View.VISIBLE
                    resultBinding.buttonError.visibility = View.VISIBLE
                },
                onActionErrorResult = {}
            ) { navDirection ->
                binding.root.children.forEach { it.visibility = View.VISIBLE }
                findNavController().navigate(navDirection)
            }
        }
    }

    private fun launchPickPhoto() {
        pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


}