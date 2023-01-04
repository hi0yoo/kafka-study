package com.example.kotlinkafkastompchat.domain.chatroom

import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository: JpaRepository<ChatRoom, Long> {
}