package com.example.messengerAndroid.app.data.model.user

import com.example.messengerAndroid.app.data.result.Result
import com.example.messengerAndroid.app.data.result.SuccessResult
import com.example.messengerAndroid.source.user.entities.CreateUserResponseEntity


class UserRepository(
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

    suspend fun editUser (user: User) {
        //TODO
//        return try {
//            userNetworkSource.editUser(user)
//        } catch (e: Exception) {
//            throw e
//        }
    }

}