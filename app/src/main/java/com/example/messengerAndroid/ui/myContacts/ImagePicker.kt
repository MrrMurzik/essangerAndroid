package com.example.messengerAndroid.ui.myContacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.example.messengerAndroid.databinding.DialogDeniedPermissionBinding
import com.example.messengerAndroid.utils.Constants
import com.example.messengerAndroid.utils.Constants.ERROR_ADDING_PHOTO

class ImagePicker(private val activity: ComponentActivity) {


    private lateinit var uri: Uri

    private val isPermissionGranted =
        activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        get() = field


    private val pickMedia = activity.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

    }

    private val requestGalleryAccessPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {

    }


    public fun getPhoto(): Uri {
        return uri
    }

    private fun requestGalleryAccessPermission() {
        requestGalleryAccessPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun showError() {
        Toast.makeText(activity, ERROR_ADDING_PHOTO, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showPermissionDeniedDialog() {
        val dialogBinding = DialogDeniedPermissionBinding.inflate(activity.layoutInflater)
        val dialog = AlertDialog.Builder(activity)
            .setView(dialogBinding.root)
            .create()
        dialog.show()
        setPermissionDeniedDialogListeners(dialogBinding, dialog)
    }

    private fun setPermissionDeniedDialogListeners(
        dialogBinding: DialogDeniedPermissionBinding,
        dialog: AlertDialog
    ) {
        dialogBinding.buttonGrantPermission.setOnClickListener {
            if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                requestGalleryAccessPermission()
            } else {
                openAppSettings()
            }
            dialog.dismiss()
        }
        dialogBinding.buttonCancel.setOnClickListener {
            //TODO
        }
    }


    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(Constants.SETTINGS_PACKAGE_SCHEME, activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    fun handlePermission() {
        requestGalleryAccessPermission()
    }

}