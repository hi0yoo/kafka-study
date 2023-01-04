package com.example.kotlinwebsocketchat.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpSession

@Controller
class ChatController {

    @GetMapping("/")
    fun index() = "error"

    @GetMapping("/{id}")
    fun chattingRoom(@PathVariable id: String, session: HttpSession, model: Model): String {
        when(id) {
            "guest" -> model.addAttribute("name", "guest")
            "master" -> model.addAttribute("name", "master")
            "loose" -> model.addAttribute("name", "loose")
            else -> return "error"
        }
        return "chat-room"
    }
}