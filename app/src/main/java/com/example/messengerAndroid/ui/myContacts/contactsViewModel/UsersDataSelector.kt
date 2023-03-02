package com.example.messengerAndroid.ui.myContacts.contactsViewModel

import com.example.messengerAndroid.data.contactsRepository.contactModel.User

interface UsersDataSelector {
    fun getUsers() : MutableList<User>
}