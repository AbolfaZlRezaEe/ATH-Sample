package com.abproject.athsample.util

/**
 * Created by Abolfazl on 5/20/21
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?, message: String?) : Resource<T>(data, message)
    class Error<T>(data: T?, message: String?) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}