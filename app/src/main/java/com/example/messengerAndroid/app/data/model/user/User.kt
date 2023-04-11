package com.example.messengerAndroid.app.data.model.user

import java.util.*

data class User(
    val id: Long,
    val name: String,
    val phone: String? = null,
    val career: String? = null,
    val address: String? = null,
    val birthday: Date? = null,
    val facebook: String? = null,
    val instagram: String? = null,
    val twitter: String? = null,
    val linkedin: String? = null,
)