package com.example.kotlinkafkastompchat.domain.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {
    fun saveUser(username: String): Long {
        var user = userRepository.findByUsername(username) ?: User(username)
        if (user.id == null) {
            user = userRepository.save(user)
        }
        return user.id!!
    }

    fun findUser(userId: Long): UserDto = UserDto(userRepository.findById(userId).orElseThrow())
}