package com.example.messengerAndroid.app.data.model

data class Contact (
    val id: String,
    val photo: String,
    val name: String,
    val job: String,
    val phone: String,
    val address: String
)

data class ContactWithState(
    val contact: Contact,
    val isMultiselectMode: Boolean,
    val isChecked: Boolean
)


