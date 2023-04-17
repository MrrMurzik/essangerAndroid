package com.example.messengerAndroid.app.foundation.utils

import java.util.*

object UniqueIdGenerator {
    fun getUniqueId() = UUID.randomUUID().toString()
}