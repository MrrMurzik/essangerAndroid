package com.example.messengerAndroid.app.foundation.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.messengerAndroid.app.App
import com.example.messengerAndroid.app.ui.auth.signUp.SignUpViewModel
import com.example.messengerAndroid.app.ui.contactDetails.ViewDetailsViewModel
import com.example.messengerAndroid.app.ui.viewPager.myContacts.contactsViewModel.ContactsViewModel

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

            SignUpViewModel::class.java -> {
                SignUpViewModel(app.userRepository)
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }

        }
        return viewModel as T
    }
}