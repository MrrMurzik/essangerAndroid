package com.example.messengerAndroid.ui.viewPager.myContacts.adapter

import com.example.messengerAndroid.data.contactsRepository.contactModel.UserWithState

interface UserActionListener {
    fun onUserDelete (userWithState: UserWithState)

    fun onItemClicked(user: UserWithState)

    fun onChangeMode()

    fun onItemClickedChooseMode(userWithState: UserWithState)

}