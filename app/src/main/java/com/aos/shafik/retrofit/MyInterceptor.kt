package com.aos.shafik.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Singleton

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@Singleton
class MyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val response = chain.proceed(originalRequest)
        Log.e(
            "interceptor",
            "${response.code}; [${response.request.method}] ${response.request.url}}"
        )
        return response
    }
}