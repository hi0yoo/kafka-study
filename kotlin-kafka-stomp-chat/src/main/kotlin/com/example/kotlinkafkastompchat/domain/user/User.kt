package com.example.kotlinkafkastompchat.domain.user

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    val username: String
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null
}