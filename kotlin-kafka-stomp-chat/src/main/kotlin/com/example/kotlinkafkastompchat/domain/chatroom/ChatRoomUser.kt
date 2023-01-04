package com.example.kotlinkafkastompchat.domain.chatroom

import com.example.kotlinkafkastompchat.domain.user.User
import javax.persistence.*

@Entity
class ChatRoomUser(
    user: User,
    chatRoom: ChatRoom
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_user_id")
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom

    init {
        this.user = user
        this.chatRoom = chatRoom
    }
}