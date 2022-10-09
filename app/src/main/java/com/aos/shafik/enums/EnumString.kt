package com.aos.shafik.enums

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
enum class EnumString(private var strValue: String) {
    AUTH_ERROR("Authentication error"),
    METHOD("album.search"),
    ALBUM("aa"),
    FORMAT_JSON("json");

    fun getValue(): String {
        return strValue
    }
}