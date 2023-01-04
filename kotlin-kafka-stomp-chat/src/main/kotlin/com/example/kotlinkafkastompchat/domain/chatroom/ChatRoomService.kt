package com.example.kotlinkafkastompchat.domain.chatroom

import com.example.kotlinkafkastompchat.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChatRoomService(
    private val userRepository: UserRepository,
    private val chatRoomRepository: ChatRoomRepository
) {
    fun createChatRoom(userId: Long, chatRoomName: String): Long {
        val user = userRepository.findById(userId).orElseThrow()
        return chatRoomRepository.save(ChatRoom(chatRoomName, user)).id!!
    }

    fun getChatRoomList(): List<ChatRoomDto> {
        return chatRoomRepository.findAll().map { ChatRoomDto(it.id!!, it.name, it.hostUser.id!!) }
    }

    fun getChatRoom(chatRoomId: Long): ChatRoomDto {
        val chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow()

        return ChatRoomDto(
            chatRoom.id!!,
            chatRoom.name,
            chatRoom.hostUser.id!!
        )
    }
}