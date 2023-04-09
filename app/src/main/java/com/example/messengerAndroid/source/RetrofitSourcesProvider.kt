package com.example.messengerAndroid.source

import com.example.messengerAndroid.app.data.SourcesProvider
import com.example.messengerAndroid.app.data.model.user.UserNetworkSource
import com.example.messengerAndroid.source.user.RetrofitUserNetworkSource

class RetrofitSourcesProvider(
    private val config: RetrofitConfig
) : SourcesProvider{

    override fun getUserNetworkSource(): UserNetworkSource {
        return RetrofitUserNetworkSource(config)
    }
}