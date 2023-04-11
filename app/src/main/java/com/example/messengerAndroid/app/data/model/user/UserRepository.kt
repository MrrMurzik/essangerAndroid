package com.example.messengerAndroid.app.data.model.user

import com.example.messengerAndroid.app.data.result.Result
import com.example.messengerAndroid.app.data.result.SuccessResult
import com.example.messengerAndroid.source.user.entities.CreateUserResponseEntity
import com.example.messengerAndroid.source.user.entities.EditUserEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(
    private val userNetworkSource: UserNetworkSource
) {

    suspend fun createUser (
        email: String,
        password: String): Result<CreateUserResponseEntity> {
        return try {
            val result = userNetworkSource.createUser(email, password)
            SuccessResult(result)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun editUser (name: String, phone: String, photo: String?): EditUserEntity  {
        return try {
            userNetworkSource.editUser(name, phone, photo)
        } catch (e: Exception) {
            throw e
        }
    }

}