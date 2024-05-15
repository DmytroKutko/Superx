package com.superx.heroes.util

sealed interface Response<out T> {
    data object Loading : Response<Nothing>
    data object Idle : Response<Nothing>

    data class Error(val error: Throwable) : Response<Nothing>
    data class Success<out T>(val data: T) : Response<T>
}