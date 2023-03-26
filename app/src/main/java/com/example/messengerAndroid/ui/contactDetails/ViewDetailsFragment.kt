package com.example.messengerAndroid.ui.contactDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.messengerAndroid.databinding.FragmentViewDetailsBinding
import com.example.messengerAndroid.extensions.addPhoto
import com.example.messengerAndroid.extensions.factory
import com.example.messengerAndroid.ui.base.BaseFragment

class ViewDetailsFragment : BaseFragment<FragmentViewDetailsBinding>(FragmentViewDetailsBinding::inflate) {


    private val viewModel: ViewDetailsViewModel by viewModels{ factory() }
    private val args: ViewDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadUser(args.id)
        setUserInfo()
        setListeners()
    }

    private fun setUserInfo() {
        with(binding) {
            textViewName.text = viewModel.usersDetails.value?.user?.name
            imageViewPicture.addPhoto(viewModel.usersDetails.value?.user?.photo?: "")
            textViewAddress.text = viewModel.usersDetails.value?.user?.address
            textViewJob.text = viewModel.usersDetails.value?.user?.job
        }
    }

    private fun setListeners() {
        binding.imageButtonArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}