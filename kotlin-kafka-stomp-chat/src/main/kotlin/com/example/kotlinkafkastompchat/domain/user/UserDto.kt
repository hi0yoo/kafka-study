package com.example.kotlinkafkastompchat.domain.user

data class UserDto(
    val userId: Long,
    val username: String
) {
    constructor(user: User) : this(user.id!!, user.username)
}
