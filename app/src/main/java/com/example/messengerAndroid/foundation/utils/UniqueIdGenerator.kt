package com.example.messengerAndroid.foundation.utils

import java.util.*

object UniqueIdGenerator {
    fun getUniqueId() = UUID.randomUUID().toString()
}