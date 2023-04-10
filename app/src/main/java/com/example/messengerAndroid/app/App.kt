package com.example.messengerAndroid.app

import android.app.Application
import com.example.messengerAndroid.app.data.UsersService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

//    private val sourcesProvider: SourcesProvider = SourceProviderHolder.sourcesProvider

    val usersService = UsersService(this)
//    val userRepository: UserRepository = UserRepository(sourcesProvider.getUserNetworkSource())






}
