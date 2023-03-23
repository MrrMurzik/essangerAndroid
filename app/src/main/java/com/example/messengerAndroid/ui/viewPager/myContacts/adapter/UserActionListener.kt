package com.example.messengerAndroid.ui.viewPager.myContacts.adapter

import com.example.messengerAndroid.data.contactsRepository.contactModel.User

interface UserActionListener {
    fun onUserDelete (user: User)

    fun onItemClicked(user: User)

    fun updateRecycler()

}