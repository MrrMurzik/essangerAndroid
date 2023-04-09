package com.example.messengerAndroid.source

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.messengerAndroid.app.foundation.exceptions.BackendException
import com.example.messengerAndroid.app.foundation.exceptions.ConnectionException
import com.example.messengerAndroid.app.foundation.exceptions.ParseBackendResponseException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import okio.IOException
import retrofit2.HttpException

open class BaseRetrofitSource(retrofitConfig: RetrofitConfig) {

    val retrofit = retrofitConfig.retrofit

    private val errorAdapter =
        retrofitConfig.moshi.adapter(ErrorResponseBody::class.java)

    /**
     * convert exceptions into in-app exceptions
     * @throws
     */
    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: JsonDataException) {
            throw ParseBackendResponseException(e)
        } catch (e: JsonEncodingException) {
            throw ParseBackendResponseException(e)
        } catch (e: HttpException) {
            throw createBackendException(e)
        } catch (e: NetworkErrorException) {
            throw ConnectionException(e)
        } catch (e: IOException) {
            throw ConnectionException(e)
        }
    }

    private fun createBackendException(e: HttpException): Exception {
        return try {
            val errorBody = errorAdapter.fromJson(
                e.response()!!.errorBody()!!.string()
            )!!
            Log.d("myTag", "${e.code()}\n${errorBody.error}")
            BackendException(e.code(), errorBody.error)
        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }

    }

    class ErrorResponseBody(
        val error: String
    )

}