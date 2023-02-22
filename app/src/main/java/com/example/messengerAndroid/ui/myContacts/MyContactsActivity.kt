package com.example.messengerAndroid.ui.myContacts

import android.Manifest.permission.READ_CONTACTS
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.contactsRepository.UsersListGenerator
import com.example.messengerAndroid.databinding.ActivityMyContactsBinding
import com.example.messengerAndroid.databinding.DialogAddContactBinding
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.ui.myContacts.adapter.ContactsAdapter
import com.example.messengerAndroid.ui.myContacts.adapter.UserActionListener
import com.example.messengerAndroid.ui.myContacts.contactsViewModel.ContactsViewModel
import com.example.messengerAndroid.ui.myContacts.contactsViewModel.SwipeToDeleteCallback
import com.example.messengerAndroid.ui.myContacts.contactsViewModel.UsersDataSelector
import com.example.messengerAndroid.ui.myContacts.contactsViewModel.factory.ContactsViewModelFactory
import com.example.messengerAndroid.utils.Constants.READ_CONTACTS_REQUEST_CODE
import com.google.android.material.snackbar.Snackbar


class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyContactsBinding
    private val isCheckedFetching = true

    private val viewModel: ContactsViewModel by viewModels {
        ContactsViewModelFactory(usersDataSelector = object : UsersDataSelector {

            override fun getUsers(): MutableList<User> {
                return if (isCheckedFetching) {
                    requestPermissions(arrayOf(READ_CONTACTS), READ_CONTACTS_REQUEST_CODE)
                    if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        fetchContacts()
                    } else {
                        UsersListGenerator().getUsers()
                    }
                } else {
                    UsersListGenerator().getUsers()
                }
            }

        })
    }

    private val adapterContacts: ContactsAdapter by lazy {
        ContactsAdapter(actionListener = object : UserActionListener {

            override fun onUserDelete(user: User) {
                val listener = getDeletionAlertDialogListener(user)
                showDeletionAlertDialog(listener)
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initRecycler()

        addSwipeToDeleteFeature()
        setListeners()
        setObservers()

    }

    private fun initRecycler() {
        binding.recyclerContacts.run {
            adapter = adapterContacts
            layoutManager = LinearLayoutManager(this@MyContactsActivity)
        }
    }

    private fun addSwipeToDeleteFeature() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.absoluteAdapterPosition
                val user = viewModel.contactsLiveData.value?.get(position)!!
                viewModel.deleteUser(user)
                showUndoSnackbar(user, position, viewModel, binding)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerContacts)
    }

    private fun setListeners() {
        binding.textViewAddContacts.setOnClickListener {
            val dialogBinding = DialogAddContactBinding.inflate(layoutInflater)
            val listener = getAddUserDialogListener(dialogBinding)

            AlertDialog.Builder(this).setTitle(R.string.add_contact_title)
                .setView(dialogBinding.root).setPositiveButton(R.string.action_confirmed, listener)
                .setNegativeButton(R.string.action_cancelled, null).create().show()
        }
    }

    private fun setObservers() {
        viewModel.contactsLiveData.observe(this) {
            adapterContacts.submitList(it.toMutableList())
        }
    }

    private fun getAddUserDialogListener(dialogBinding: DialogAddContactBinding):
            DialogInterface.OnClickListener {

        return DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.addNewUser(
                        dialogBinding.textInputName.editText?.text.toString(),
                        dialogBinding.textInputJob.editText?.text.toString()
                    )
                }
            }
        }

    }

    private fun getDeletionAlertDialogListener(user: User): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val userPosition = viewModel.getUserPosition(user)
                    viewModel.deleteUser(user)
                    showUndoSnackbar(user, userPosition, viewModel, binding)
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    showToast(getString(R.string.toast_cancelled_deletion))
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showUndoSnackbar(
        user: User, position: Int, viewModel: ContactsViewModel, binding: ActivityMyContactsBinding
    ) {
        Snackbar.make(
                binding.root, R.string.deletion_snackbar_message, Snackbar.LENGTH_LONG
            ).setAction(R.string.undo_delete_action) { viewModel.addExistingUser(user, position) }
            .setActionTextColor(Color.CYAN).show()
    }

    private fun showDeletionAlertDialog(listener: DialogInterface.OnClickListener) {

        AlertDialog.Builder(this).setCancelable(true)
            .setTitle(R.string.default_deletion_dialog_title)
            .setMessage(R.string.default_deletion_dialog_message)
            .setTitle(R.string.default_deletion_dialog_title)
            .setMessage(R.string.default_deletion_dialog_message)
            .setPositiveButton(R.string.action_confirmed, listener)
            .setNegativeButton(R.string.action_cancelled, listener).create().show()
    }


    fun fetchContacts(): MutableList<User> {

        val contacts = mutableListOf<User>()
        val cursor = this@MyContactsActivity.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val id =
                    it.getLong(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                val name =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val photo =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI))
                val contact = User(id, "", name, "")
                contacts.add(contact)
            }
        }
        return contacts
    }


}