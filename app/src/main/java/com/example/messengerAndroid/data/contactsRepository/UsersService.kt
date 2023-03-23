package com.example.messengerAndroid.data.contactsRepository

import android.Manifest.permission.READ_CONTACTS
import android.app.Application
import android.content.pm.PackageManager
import com.example.messengerAndroid.data.contactsRepository.contactModel.User

class UsersService(private val appContext: Application) {

    var usersList = listOf<User>()

    fun getUsers(): List<User> {
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