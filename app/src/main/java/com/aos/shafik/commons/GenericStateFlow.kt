package com.aos.shafik.commons

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
sealed class GenericStateFlow<out T> {
    object Loading : GenericStateFlow<Nothing>()
    object Empty : GenericStateFlow<Nothing>()
    data class Error(val exception: Throwable) : GenericStateFlow<Nothing>()
    data class Success<T>(val data: T) : GenericStateFlow<T>()
}