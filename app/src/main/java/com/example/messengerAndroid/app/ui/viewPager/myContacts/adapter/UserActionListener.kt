package com.example.messengerAndroid.app.ui.viewPager.myContacts.adapter

import com.example.messengerAndroid.app.data.model.ContactWithState

interface UserActionListener {
    fun onUserDelete (contactWithState: ContactWithState)

    fun onItemClicked(user: ContactWithState)

    fun onChangeMode()

    fun onItemClickedChooseMode(contactWithState: ContactWithState)

}