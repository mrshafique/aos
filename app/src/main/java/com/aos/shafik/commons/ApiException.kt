package com.aos.shafik.commons

import android.util.Log
import com.aos.shafik.enums.EnumString
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
class ApiException {
    companion object {
        /**
         * @Usage: Depending on exception it will return GenericStateFlow.Error with errorMessage
         * @Author: Shafik Shaikh
         */
        fun resolveError(e: Exception): GenericStateFlow.Error {
            var error = e
            when (e) {
                is SocketTimeoutException ->
                    error = NetworkErrorException(errorMessage = "Connection error!")
                is ConnectException ->
                    error = NetworkErrorException(errorMessage = "No internet access!")
                is UnknownHostException ->
                    error = NetworkErrorException(errorMessage = "No internet access!")
                is HttpException -> {
                    Log.e("ApiException", "code: ${e.code()}")
                    error = when (e.code()) {
                        502 -> NetworkErrorException(e.code(), "Internal error!")
                        401 -> NetworkErrorException(
                            e.code(),
                            EnumString.AUTH_ERROR.getValue()
                        )
                        else -> NetworkErrorException.parseException(e) //400
                    }
                }
            }
            return GenericStateFlow.Error(error)
        }
    }

    /**
     * @Author: Shafik Shaikh
     * @Date: 09-10-2022
     */
    open class NetworkErrorException(
        val errorCode: Int = -1,
        val errorMessage: String,
        val response: String = ""
    ) : Exception() {
        override val message: String
            get() = localizedMessage

        override fun getLocalizedMessage(): String {
            return errorMessage
        }

        companion object {
            fun parseException(e: HttpException): NetworkErrorException {
                val errorBody = e.response()?.errorBody()?.string()

                return try {//here you can parse the error body that comes from server
                    NetworkErrorException(
                        e.code(),
                        JSONObject(errorBody!!).getString("message")
                    )
                } catch (_: Exception) {
                    NetworkErrorException(e.code(), "Unexpected error!!ً")
                }
            }
        }
    }

    /**
     * @Author: Shafik Shaikh
     * @Date: 09-10-2022
     */
    class AuthenticationException(authMessage: String) :
        NetworkErrorException(errorMessage = authMessage)
}