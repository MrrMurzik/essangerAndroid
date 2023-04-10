package com.example.messengerAndroid.source.user

import com.example.messengerAndroid.app.data.model.user.User
import com.example.messengerAndroid.app.data.model.user.UserNetworkSource
import com.example.messengerAndroid.source.BaseRetrofitSource
import com.example.messengerAndroid.source.RetrofitConfig
import com.example.messengerAndroid.source.user.entities.CreateUserRequestEntity
import com.example.messengerAndroid.source.user.entities.EditUserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitUserNetworkSource @Inject constructor(config: RetrofitConfig)
    : BaseRetrofitSource(config), UserNetworkSource {

    private val userApi = retrofit.create(UserApi::class.java)


    override suspend fun createUser(email: String, password: String) = wrapRetrofitExceptions {
        val createUserRequestEntity = CreateUserRequestEntity(email = email, password = password)
        userApi.createUser(createUserRequestEntity)
    }

    override suspend fun editUser(user: User): EditUserEntity {
        TODO("Not yet implemented")
    }

//    override suspend fun editUser(user: User): EditUserEntity = wrapRetrofitExceptions {
////        val editUserRequestEntity = EditUserEntity(
////            name = user.name,
////            phone = user.phone
////        )
////        userApi.editUser(
////            editUserEntity = editUserRequestEntity, )
//
//    }
}