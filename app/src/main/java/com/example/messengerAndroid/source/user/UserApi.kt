package com.example.messengerAndroid.source.user

import com.example.messengerAndroid.source.user.entities.CreateUserRequestEntity
import com.example.messengerAndroid.source.user.entities.CreateUserResponseEntity
import com.example.messengerAndroid.source.user.entities.EditUserEntity
import retrofit2.http.*

interface UserApi {

    @POST("users")
    suspend fun createUser(
        @Body createUserRequestEntity: CreateUserRequestEntity,
    ) : CreateUserResponseEntity


    @PUT("users/{userId}")
    suspend fun editUser(
        @Path("userId") userId: String,
        @Body editUserEntity: EditUserEntity,
        @Header ("Content-type") contentType: String = "application/json",
        @Header("Authorization") accessToken: String,
    ) : EditUserEntity



}