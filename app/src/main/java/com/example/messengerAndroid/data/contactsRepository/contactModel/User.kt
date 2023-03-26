package com.example.messengerAndroid.data.contactsRepository.contactModel

data class User (
    val id: String,
    val photo: String,
    val name: String,
    val job: String,
    val phone: String,
    val address: String
)

data class UserWithState(
    val user: User,
    val isMultiselectMode: Boolean,
    val isChecked: Boolean
)


