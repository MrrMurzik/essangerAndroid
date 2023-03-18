package com.example.messengerAndroid.ui.navigation

import com.example.messengerAndroid.data.contactsRepository.contactModel.User

interface Navigator {

    fun showSignUpScreen()

    fun showMyContactsScreen(isFetched: Boolean)

    fun showMyProfileScreen()

    fun signOut()

    fun showViewDetails(user: User)

}