package com.example.messengerAndroid.app.data.result

import com.example.messengerAndroid.app.ui.auth.signUp.Error

typealias Mapper<Input, Output> = (Input) -> Output

sealed class Result<T> {
    fun <R> map(mapper: Mapper<T, R>? = null): Result<R> = when (this) {
        is PendingResult -> PendingResult()
        is SystemErrorResult -> SystemErrorResult(this.error)
        is ActionErrorResult -> SystemErrorResult(this.error)
        is SuccessResult -> {
            if (mapper == null) throw IllegalStateException()
            SuccessResult(mapper(this.data))
        }

    }
}

class PendingResult<T> : Result<T>()

class SuccessResult<T>(val data: T) : Result<T>()

class SystemErrorResult<T>(val error: Error) : Result<T>()

class ActionErrorResult<T>(val error: Error) : Result<T>()

