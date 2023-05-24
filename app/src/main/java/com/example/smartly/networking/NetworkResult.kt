package com.example.smartly.networking

import okhttp3.Headers

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val e: Exception? = null,
    val errorCode: Int? = null,
    val headers: Headers? = null,
    val value: String? = null
) {
    class Success<T>(data: T, headers: Headers? = null) : NetworkResult<T>(data, headers = headers)
    class Error<T>(message: String, e: Exception? = null, errorCode: Int? = 0) : NetworkResult<T>(message = message, e = e, errorCode = errorCode)
    class Loading<T> : NetworkResult<T>()
    class NetworkError<T>(e: Exception? = null, value: String) : NetworkResult<T>(e = e, value = value)
    class ServerError<T>(e: Exception? = null, value: String) : NetworkResult<T>(e = e, value = value)
    class TimeOutError<T>(e: Exception? = null, value: String) : NetworkResult<T>(e = e, value = value)
}