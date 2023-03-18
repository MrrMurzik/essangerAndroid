package com.example.messengerAndroid.data.contactsRepository.contactModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id: String,
    val photo: String,
    val name: String,
    val job: String,
    val phone: String,
    val address: String
) : Parcelable