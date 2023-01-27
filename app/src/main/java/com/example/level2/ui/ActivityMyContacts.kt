package com.example.level2.ui

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.level2.R
import com.example.level2.databinding.MyContactsActivityBinding
import com.example.level2.model.User
import com.example.level2.viewmodel.ContactsViewModel
import com.google.android.material.snackbar.Snackbar

// dependency injection (hilt, coin, dagger)

class ActivityMyContacts : AppCompatActivity(), UserActionListener {

    private lateinit var binding: MyContactsActivityBinding

    private val adapterContacts: ContactsAdapter by lazy {
        ContactsAdapter(actionListener = this@ActivityMyContacts)
    }

    private val viewModel: ContactsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyContactsActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initRecycler()

        setObservers()

    }

    private fun initRecycler() {
        binding.recyclerContacts.run {
            adapter = adapterContacts
            layoutManager = LinearLayoutManager(this@ActivityMyContacts)
        }
    }

    private fun setObservers() {
       viewModel.contactsLiveData.observe(this){
            adapterContacts.submitList(it.toMutableList())
       }
    }

    override fun onUserDelete(user: User) {
        showDeletionAlertDialog(getAlertDialogListener(user))
    }

    private fun getAlertDialogListener(user: User): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val userPosition = viewModel.getUserPosition(user)
                    viewModel.deleteUser(user)
                    showUndoSnackbar(user, userPosition)
                }
                DialogInterface.BUTTON_NEGATIVE -> showToast("Deletion has cancelled")
            }
        }
    }

    private fun showDeletionAlertDialog(alertDialogListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setCancelable(true)
            .setTitle(R.string.default_deletion_dialog_title)
            .setMessage(R.string.default_deletion_dialog_message)
            .setPositiveButton("Yes", alertDialogListener)
            .setNegativeButton("No", alertDialogListener)
            .create()
            .show()
    }

    private fun showUndoSnackbar(user: User, position: Int) {
        Snackbar
            .make(
                binding.root,
                R.string.deletion_snackbar_message,
                5000)
            .setAction("Undo") {addUser(user, position)}
            .setActionTextColor(Color.CYAN)
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun addUser(user: User, position: Int) {
        viewModel.addUser(user, position)
    }


}