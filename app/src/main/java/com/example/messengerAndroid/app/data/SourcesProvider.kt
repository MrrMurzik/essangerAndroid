package com.example.messengerAndroid.app.data

import com.example.messengerAndroid.app.data.model.user.UserNetworkSource

interface SourcesProvider {

    fun getUserNetworkSource(): UserNetworkSource

}