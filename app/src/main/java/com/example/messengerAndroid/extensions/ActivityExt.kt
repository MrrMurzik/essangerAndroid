package com.example.messengerAndroid.extensions

import android.app.Activity
import android.content.DialogInterface.OnClickListener
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.databinding.ActivityMyContactsBinding
import com.example.messengerAndroid.databinding.DialogAddContactBinding
import com.example.messengerAndroid.ui.contactsViewModel.ContactsViewModel
import com.google.android.material.snackbar.Snackbar

fun Activity.showDeletionAlertDialog(listener: OnClickListener) {

    AlertDialog.Builder(this)
        .setCancelable(true)
        .setTitle(R.string.default_deletion_dialog_title)
        .setMessage(R.string.default_deletion_dialog_message)
        .setTitle(R.string.default_deletion_dialog_title)
        .setMessage(R.string.default_deletion_dialog_message)
        .setPositiveButton(R.string.action_confirmed, listener)
        .setNegativeButton(R.string.action_cancelled, listener)
        .create()
        .show()
}

fun Activity.showAddUserAlertDialog(listener: OnClickListener) {
    val binding: DialogAddContactBinding = DialogAddContactBinding.inflate(layoutInflater)
    AlertDialog.Builder(this)
        .setTitle(R.string.add_contact_title)
        .setView(binding.root)
        .setPositiveButton(R.string.action_confirmed, listener)
        .setNegativeButton(R.string.action_cancelled, null)
        .create()
        .show()
}


