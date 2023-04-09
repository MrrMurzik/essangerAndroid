package com.example.messengerAndroid.app.data.contactsRepository

import com.example.messengerAndroid.app.data.result.Result

interface ContactRepository {

    fun <T>registerUser(email: String, password: String): Result<T>

}