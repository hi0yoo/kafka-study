package com.example.kotlinkafkastompchat.utils

import javax.servlet.http.HttpServletRequest

class CookieUtils {
    companion object {
        fun getUserId(httpRequest: HttpServletRequest): Long? {
            val userIdCookie = httpRequest.cookies.firstOrNull { it.name == "uid" }
            return userIdCookie?.value?.toLong()
        }
    }
}