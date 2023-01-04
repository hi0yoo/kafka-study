package com.example.kotlinkafkastompchat.web.chatroom.controller

import com.example.kotlinkafkastompchat.domain.chatroom.ChatRoomService
import com.example.kotlinkafkastompchat.utils.CookieUtils
import com.example.kotlinkafkastompchat.web.chatroom.request.CreateChatRoomRequest
import com.example.kotlinkafkastompchat.web.chatroom.response.ChatRoomDetailResponse
import com.example.kotlinkafkastompchat.web.chatroom.response.CreateChatRoomResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/chat-rooms")
class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {
    companion object {
        private val log = LoggerFactory.getLogger(ChatRoomController::class.java)
    }

    @GetMapping
    fun chatRoomListPage(
        model: Model,
        request: HttpServletRequest
    ): String {
        isLoggedIn(request)

        model.addAttribute("chatRooms", chatRoomService.getChatRoomList())
        return "chat-room/list-page"
    }

    private fun isLoggedIn(request: HttpServletRequest): Long {
        val userId = CookieUtils.getUserId(request)
        if (userId == null) throw RuntimeException("인증되지 않은 사용자의 접근")
        else return userId
    }

    @GetMapping("/new")
    fun createChatRoomPage(request: HttpServletRequest): String {
        isLoggedIn(request)

        return "chat-room/create-page"
    }

    @ResponseBody
    @PostMapping
    fun createChatRoom(
        @RequestBody createChatRoomRequest: CreateChatRoomRequest,
        request: HttpServletRequest
    ): CreateChatRoomResponse {
        val userId = isLoggedIn(request)
        val chatRoomId = chatRoomService.createChatRoom(
            userId,
            createChatRoomRequest.chatRoomName!!
        )

        log.info("chat-room(id: $chatRoomId) create success")

        return CreateChatRoomResponse(chatRoomId)
    }

    @GetMapping("/{chatRoomId}")
    fun chatRoomDetailPage(
        @PathVariable chatRoomId: Long,
        request: HttpServletRequest,
        model: Model
    ): String {
        isLoggedIn(request)

        val chatRoomDto = chatRoomService.getChatRoom(chatRoomId)
        model.addAttribute(
            "chatRoomInfo",
            ChatRoomDetailResponse(
                chatRoomDto.chatRoomId,
                chatRoomDto.chatRoomName,
                chatRoomDto.hostUserId
            )
        )

        return "chat-room/detail-page"
    }
}