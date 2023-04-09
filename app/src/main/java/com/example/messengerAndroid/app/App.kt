package com.example.messengerAndroid.app

import android.app.Application
import com.example.messengerAndroid.app.data.SourcesProvider
import com.example.messengerAndroid.app.data.UsersService
import com.example.messengerAndroid.app.data.contactsRepository.NetworkRepositoryImpl
import com.example.messengerAndroid.app.data.model.user.UserRepository
import com.example.messengerAndroid.source.SourceProviderHolder

class App : Application() {

    private val sourcesProvider: SourcesProvider = SourceProviderHolder.sourcesProvider

    val usersService = UsersService(this)
    val userRepository: UserRepository = UserRepository(sourcesProvider.getUserNetworkSource())






}
