package com.example.messengerAndroid.source.user.entities

data class CreateUserRequestEntity(
    val email: String,
    val password: String
)

fun CreateUserRequestEntity.toQueryMap(): Map<String, String> {
    return mapOf("email" to email, "password" to password)
}