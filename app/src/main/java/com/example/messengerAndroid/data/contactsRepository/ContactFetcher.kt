package com.example.messengerAndroid.data.contactsRepository

import android.content.Context
import android.provider.ContactsContract
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.data.contactsRepository.contactModel.UserWithState
import com.example.messengerAndroid.foundation.utils.UniqueIdGenerator.getUniqueId

class ContactFetcher {

    fun fetchContacts(context: Context): List<UserWithState> {
        val contacts = mutableListOf<UserWithState>()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val name =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val photo: String? =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                val phone = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                //TODO
                // I cant retrieve information from provider about job and postal address
                val job = ""
                val postalAddress = ""

                val contact = UserWithState(User(getUniqueId(), photo?: "", name, job, phone, postalAddress),
                    isMultiselectMode = false,
                    isChecked = false
                )
                contacts.add(contact)
            }
        }
        cursor?.close()
        return contacts.toList()
    }
}