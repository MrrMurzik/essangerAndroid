package com.example.messengerAndroid.ui.myContacts.adapter

import com.example.messengerAndroid.data.contactsRepository.contactModel.User

interface UserActionListener {
    fun onUserDelete (user: User)
}