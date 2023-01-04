package com.example.kotlinkafkastompchat.domain.chatroom

import com.example.kotlinkafkastompchat.domain.user.User
import javax.persistence.*

@Entity
class ChatRoom(
    var name: String,
    hostUser: User
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    val id: Long? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_user_id")
    var hostUser: User

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL])
    var chatRoomUsers = mutableListOf<ChatRoomUser>()
        private set

    init {
        this.hostUser = hostUser
        addUser(hostUser)
    }

    fun addUser(user: User) {
        this.chatRoomUsers.add(ChatRoomUser(user, this))
    }
}