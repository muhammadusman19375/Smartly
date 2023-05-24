package com.example.smartly.networking

import android.util.Log
import okhttp3.Headers
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            Log.d("BaseApiResponse", "safeApiCall: ${apiCall.javaClass}")
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                val headers: Headers = response.headers()
                body?.let {
                    Log.d("BaseApiResponse", "safeApiCall: ${apiCall.javaClass} Success : ${response.message()}")
                    return NetworkResult.Success(body, headers = headers)
                }
            }
            if (response.errorBody() != null) {
                val errorCode = response.raw().code()
                Log.d("BaseApiResponse", "safeApiCall: ${apiCall.javaClass} Error : ${response.errorBody().toString()}")
                return NetworkResult.Error(message = "Something went wrong. Please try again later", errorCode = errorCode)
            }
            return NetworkResult.Error(message = "${response.code()} ${response.message()}", errorCode = response.code())
        } catch (e: Exception) {
            when (e) {
                is ConnectException -> {
                    return NetworkResult.ServerError(e, "No internet connection")
                }
                is UnknownHostException -> {
                    return NetworkResult.NetworkError(e, "Unknown host exception")
                }
                is TimeoutException -> {
                    return NetworkResult.TimeOutError(e, "Timeout exception")
                }
            }
            return NetworkResult.Error(e.message ?: e.toString(), e)

        }
    }
}