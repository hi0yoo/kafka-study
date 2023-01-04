package com.example.kotlinkafkastompchat.domain.chatroom

data class ChatRoomDto(
    val chatRoomId: Long,
    val chatRoomName: String,
    val hostUserId: Long
)