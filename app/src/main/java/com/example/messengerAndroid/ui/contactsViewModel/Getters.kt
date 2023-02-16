package com.example.messengerAndroid.ui.contactsViewModel

import com.example.messengerAndroid.data.contactsRepository.contactModel.User

fun ContactsViewModel.getUserPosition(user: User): Int {
    return contactsLiveData.value?.indexOf(user) ?: -1
}

fun ContactsViewModel.getUser(position: Int): User {
    return contactsLiveData.value?.toMutableList()?.get(position)!!
}


