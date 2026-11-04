package me.splleat.messengercore.domain.user

import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
}