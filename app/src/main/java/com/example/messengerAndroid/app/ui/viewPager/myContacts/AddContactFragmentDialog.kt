package com.example.messengerAndroid.app.ui.viewPager.myContacts

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.messengerAndroid.databinding.DialogAddContactBinding
import com.example.messengerAndroid.app.ui.viewPager.myContacts.contactsViewModel.ContactsViewModel
import com.example.messengerAndroid.app.Constants.KEY_SAVE_STATE_JOB
import com.example.messengerAndroid.app.Constants.KEY_SAVE_STATE_NAME
import com.example.messengerAndroid.app.Constants.KEY_SAVE_STATE_PHONE_NUMBER
import com.example.messengerAndroid.app.Constants.KEY_SAVE_STATE_PHOTO_URI
import com.example.messengerAndroid.app.Constants.KEY_SAVE_STATE_POSTAL_ADDRESS

class AddContactFragmentDialog : DialogFragment() {

    private var uri = Uri.EMPTY

    private var _binding :DialogAddContactBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactsViewModel by viewModels({requireParentFragment()})

    private val pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        this.uri = uri?: Uri.EMPTY
        binding.imageViewAvatar.setImageURI(uri)
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddContactBinding.inflate(layoutInflater)
        with(binding) {

            arguments?.let {
                textInputName.editText?.setText(it.getString(KEY_SAVE_STATE_NAME))
                textInputJob.editText?.setText(it.getString(KEY_SAVE_STATE_JOB))
                textInputPhoneNumber.editText?.setText(it.getString(KEY_SAVE_STATE_PHONE_NUMBER))
                textInputPostalAddress.editText?.setText(it.getString(KEY_SAVE_STATE_POSTAL_ADDRESS))
                uri = Uri.parse(it.getString(KEY_SAVE_STATE_PHOTO_URI))
                if (uri != Uri.EMPTY) {
                    binding.imageViewAvatar.setImageURI(uri)
                }
            }

            val dialog = AlertDialog.Builder(requireContext()).setView(root).create()
            setListenerForAddImageViews(this)
            buttonConfirm.setOnClickListener(getAddUserDialogListener())
            buttonCancel.setOnClickListener { dialog.dismiss() }
            return dialog
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        pickMediaLauncher.unregister()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putString(KEY_SAVE_STATE_NAME, binding.textInputName.editText?.text.toString())
            putString(KEY_SAVE_STATE_JOB, binding.textInputJob.editText?.text.toString())
            putString(KEY_SAVE_STATE_PHOTO_URI, uri.toString())
            putString(
                KEY_SAVE_STATE_PHONE_NUMBER,
                binding.textInputPhoneNumber.editText?.text.toString()
            )
            putString(
                KEY_SAVE_STATE_POSTAL_ADDRESS,
                binding.textInputPostalAddress.editText?.text.toString()
            )
        }

    }

    private fun setListenerForAddImageViews(dialogBinding: DialogAddContactBinding) {

        val listener = View.OnClickListener {
            launchPickPhoto()
        }
        dialogBinding.textViewAddPhoto.setOnClickListener(listener)
        dialogBinding.imageViewAvatar.setOnClickListener(listener)
    }

    private fun launchPickPhoto() {
        pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun getAddUserDialogListener(): View.OnClickListener {

        return View.OnClickListener {
            viewModel.addNewUser(
                name = binding.textInputName.editText?.text.toString(),
                job = binding.textInputJob.editText?.text.toString(),
                photo = uri.toString(),
                phone = binding.textInputPhoneNumber.editText?.text.toString(),
                address = binding.textInputPostalAddress.editText?.text.toString()
            )
            dialog?.dismiss()
        }

    }

}