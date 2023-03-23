package com.example.messengerAndroid.ui.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.messengerAndroid.App
import com.example.messengerAndroid.ui.contactDetails.ViewDetailsViewModel
import com.example.messengerAndroid.ui.viewPager.myContacts.contactsViewModel.ContactsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val app: App) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {

            ContactsViewModel::class.java -> {
                ContactsViewModel(app.usersService)
            }

            ViewDetailsViewModel::class.java -> {
                ViewDetailsViewModel(app.usersService)
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }

        }
        return viewModel as T
    }
}