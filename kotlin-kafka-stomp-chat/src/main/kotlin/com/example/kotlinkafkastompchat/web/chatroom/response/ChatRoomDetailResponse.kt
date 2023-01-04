package com.example.kotlinkafkastompchat.web.chatroom.response

data class ChatRoomDetailResponse(
    val chatRoomId: Long,
    val chatRoomName: String,
    val hostUserId: Long
)