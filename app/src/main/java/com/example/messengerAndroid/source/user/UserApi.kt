package com.example.messengerAndroid.source.user

import com.example.messengerAndroid.SignUpRequestBody
import com.example.messengerAndroid.SignUpResponseBody
import com.example.messengerAndroid.source.user.entities.CreateUserRequestEntity
import com.example.messengerAndroid.source.user.entities.CreateUserResponseEntity
import com.example.messengerAndroid.source.user.entities.EditUserEntity
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface UserApi {

    @POST("users")
    suspend fun createUser(
        @Body createUserRequestEntity: CreateUserRequestEntity,
    ) : CreateUserResponseEntity


    @PUT("/users/{userId}")
    suspend fun editUser(
        @Path("userId") userId: Int,
        @Body editUserEntity: EditUserEntity,
        @Header("Authorization") accessToken: String,
        @Header("Content-type:") contentType: String = "Content-type: application/json",
    ) : EditUserEntity


}