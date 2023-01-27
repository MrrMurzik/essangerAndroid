package com.example.level2.ui

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.level2.R
import com.example.level2.databinding.MyContactsActivityBinding
import com.example.level2.model.User
import com.example.level2.viewmodel.ContactsViewModel

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
        confirmDeletion(user)
    }

    private fun confirmDeletion(user: User) {

        val alertDialogListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteUser(user)
                    showToast("User ${user.name} has deleted")
                }
                DialogInterface.BUTTON_NEGATIVE -> showToast("Deletion cancelled")
            }
        }

        val alertDialog = AlertDialog.Builder(this)
            .setCancelable(true)
            .setTitle(R.string.default_deletion_dialog_title)
            .setMessage(R.string.default_deletion_dialog_message)
            .setPositiveButton("Yes", alertDialogListener)
            .setNegativeButton("No", alertDialogListener)
            .create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}