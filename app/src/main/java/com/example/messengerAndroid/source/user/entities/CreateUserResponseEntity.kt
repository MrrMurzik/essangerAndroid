package com.example.messengerAndroid.source.user.entities

data class CreateUserResponseEntity(
    val status: String,
    val code: Int,
    val message: String,
    val data: Data
)

data class Data(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)

data class User(
    val email: String?,
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: String?,
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val linkedin: String?,
    val image: String?,
    val updated_at: String?,
    val created_at: String?,
    val id: Int?
)


