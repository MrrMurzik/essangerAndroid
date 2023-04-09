package com.example.messengerAndroid.app.data.model.user

import com.example.messengerAndroid.source.user.entities.CreateUserResponseEntity
import com.example.messengerAndroid.source.user.entities.EditUserEntity

interface UserNetworkSource {

    /**
     * register new user.
     * @throws AccountAlreadyExistsException
     * @throws AuthException
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     * @return CreateUserResponseEntity
     */
    suspend fun createUser(email: String, password: String): CreateUserResponseEntity

    /**
     * edit existent user
     * @throws AccountAlreadyExistsException
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
    suspend fun editUser(user: User): EditUserEntity

}
