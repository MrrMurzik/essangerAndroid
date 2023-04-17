package com.example.messengerAndroid.app.data

import android.Manifest.permission.READ_CONTACTS
import android.app.Application
import android.content.pm.PackageManager
import com.example.messengerAndroid.app.data.model.ContactWithState

class UsersService(private val appContext: Application) {

    var usersList = listOf<ContactWithState>()

    fun getUsers(): List<ContactWithState> {
        if (appContext.checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            setUsersFromPhone()
        } else {
            setHardcodedUsers()
        }
        return usersList
    }

    private fun setUsersFromPhone() {
        usersList = ContactFetcher().fetchContacts(appContext)
    }

    private fun setHardcodedUsers() {
        usersList = UsersListGenerator().getUsers()
    }

}