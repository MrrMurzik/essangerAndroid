package com.example.messengerAndroid.app.data

import android.content.Context
import android.provider.ContactsContract
import com.example.messengerAndroid.app.data.model.Contact
import com.example.messengerAndroid.app.data.model.ContactWithState
import com.example.messengerAndroid.app.foundation.utils.UniqueIdGenerator.getUniqueId

class ContactFetcher {

    fun fetchContacts(context: Context): List<ContactWithState> {
        val contacts = mutableListOf<ContactWithState>()
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

                val contact = ContactWithState(
                    Contact(getUniqueId(), photo?: "", name, job, phone, postalAddress),
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