package com.example.messengerAndroid.source.user.entities

data class CreateUserResponseEntity(
    val id: String,
    val accessToken: String,
    val refreshToken: String
)