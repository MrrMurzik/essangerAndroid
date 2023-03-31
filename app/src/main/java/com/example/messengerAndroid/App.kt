package com.example.messengerAndroid

import android.app.Application
import com.example.messengerAndroid.data.contactsRepository.UsersService

class App : Application() {

    val usersService = UsersService(this)

}
