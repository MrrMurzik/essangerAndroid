package com.example.messengerAndroid.source.user

import android.util.Log
import com.example.messengerAndroid.app.data.model.user.UserNetworkSource
import com.example.messengerAndroid.app.data.preferences.SharedPreferencesHelper
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
        val response = userApi.createUser(createUserRequestEntity)
        Log.d("myTag", "response in network: $response")
        response
    }

    //todo loading image
    override suspend fun editUser(
        name: String,
        phone: String,
        photo: String?
    ): EditUserEntity = wrapRetrofitExceptions {
        val id = SharedPreferencesHelper.getNetworkId()
        val token = SharedPreferencesHelper.getToken()
        val accessToken = "Bearer $token"
        val editUserEntity = EditUserEntity(name = name, phone = phone)
        Log.d("myTag", "id: $id")
        userApi.editUser(id, editUserEntity, accessToken = accessToken)

    }

}