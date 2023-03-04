package com.example.messengerAndroid.ui.myContacts

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.messengerAndroid.R
import com.example.messengerAndroid.databinding.DialogAddContactBinding
import com.example.messengerAndroid.databinding.DialogDeniedPermissionBinding
import com.example.messengerAndroid.utils.Constants

class ImagePickerFragmentDialog : DialogFragment() {

    private lateinit var uri: Uri
    private lateinit var dialogBinding: DialogAddContactBinding

    private val requestGalleryAccessPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if (!it) {
            showPermissionDeniedDialog()
        } else {
            launchPickPhoto()
        }
    }

    private val pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        this.uri = uri?: Uri.EMPTY
        dialogBinding.imageViewAvatar.setImageURI(uri)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogBinding = DialogAddContactBinding.inflate(layoutInflater)
        setListenerForAddImageViews(dialogBinding)
        val positiveButtonListener = getAddUserDialogListener()

        return AlertDialog.Builder(requireContext()).setTitle(R.string.add_contact_title)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.action_confirmed, positiveButtonListener)
            .setNegativeButton(R.string.action_cancelled, null)
            .create()

    }

    private fun setListenerForAddImageViews(dialogBinding: DialogAddContactBinding) {

        val listener = View.OnClickListener {
            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
                requestGalleryAccessPermission()
            } else {
                launchPickPhoto()
            }
        }
        dialogBinding.textViewAddPhoto.setOnClickListener(listener)
        dialogBinding.imageViewAvatar.setOnClickListener(listener)
    }

    private fun requestGalleryAccessPermission() {
        requestGalleryAccessPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun launchPickPhoto() {
        pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    private fun getAddUserDialogListener():
            DialogInterface.OnClickListener {

        return DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    Toast.makeText(requireContext(), "Positive button", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun showPermissionDeniedDialog() {
        val deniedDialogBinding = DialogDeniedPermissionBinding.inflate(requireActivity().layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialog.show()
        setPermissionDeniedDialogListeners(deniedDialogBinding, dialog)
    }

    private fun setPermissionDeniedDialogListeners(
        deniedDialogBinding: DialogDeniedPermissionBinding,
        dialog: AlertDialog
    ) {
        deniedDialogBinding.buttonGrantPermission.setOnClickListener {
            if (requireActivity().shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                requestGalleryAccessPermission()
            } else {
                openAppSettings()
            }
            dialog.dismiss()
        }
        deniedDialogBinding.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(Constants.SETTINGS_PACKAGE_SCHEME, requireActivity().packageName, null)
        intent.data = uri
        requireActivity().startActivity(intent)
    }
}