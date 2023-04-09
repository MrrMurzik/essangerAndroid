package com.example.messengerAndroid.app.ui.auth.signUpExtended

import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.messengerAndroid.databinding.FragmentSignUpExtendedBinding
import com.example.messengerAndroid.app.foundation.base.BaseFragment
import com.example.messengerAndroid.app.foundation.extensions.addPhoto

class SignUpExtendedFragment: BaseFragment<FragmentSignUpExtendedBinding>(FragmentSignUpExtendedBinding::inflate) {

    private val viewModel: SignUpExtendedViewModel by viewModels()

    private val pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        binding.imageViewPicture.addPhoto(uri?.toString()?: "")
        viewModel.registerUserPhoto(uri.toString())
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(it)
        }
    }

    private fun setListeners() {
        binding.buttonForward.setOnClickListener {
            viewModel.registerNameAndPhone(
                binding.inputLayoutName.editText?.text.toString(),
                binding.inputLayoutPhone.editText?.text.toString()
            )
            viewModel.updateDirection()
        }

        binding.imageViewAddPhoto.setOnClickListener {
            launchPickPhoto()
        }
    }

    private fun launchPickPhoto() {
        pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


}