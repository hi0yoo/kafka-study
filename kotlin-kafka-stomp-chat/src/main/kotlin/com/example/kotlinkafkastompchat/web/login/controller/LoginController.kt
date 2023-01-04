package com.example.kotlinkafkastompchat.web.login.controller

import com.example.kotlinkafkastompchat.domain.user.UserService
import com.example.kotlinkafkastompchat.web.login.request.LoginRequest
import com.example.kotlinkafkastompchat.web.login.response.LoginResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Controller
class LoginController(
    private val userService: UserService
) {
    companion object {
        private val log = LoggerFactory.getLogger(LoginController::class.java)
    }

    @GetMapping("/")
    fun loginPage(): String {
        return "login/login-page"
    }

    @ResponseBody
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest,
        response: HttpServletResponse
    ): LoginResponse {
        val userId = userService.saveUser(loginRequest.username!!)
        val userDto = userService.findUser(userId)

        log.info("user(id: ${userDto.userId}, username: ${userDto.username}) login success")
        response.addCookie(Cookie("uid", userDto.userId.toString()))

        return LoginResponse("Login Success")
    }
}