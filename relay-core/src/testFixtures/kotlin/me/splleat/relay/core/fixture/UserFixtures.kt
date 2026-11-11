package me.splleat.relay.core.fixture

import me.splleat.relay.core.domain.user.User
import me.splleat.relay.core.domain.user.UserRepository
import java.util.concurrent.atomic.AtomicLong


private val emailSequence = AtomicLong()

private fun uniqueEmail() = "user${emailSequence.incrementAndGet()}@test.com"

fun createUser(
    name: String = "테스트",
    email: String = uniqueEmail(),
    passwordHash: String = "password",
): User = User.register(name, email, passwordHash)

fun UserRepository.saveUser(
    name: String = "테스트",
    email: String = uniqueEmail()
): User = save(createUser(name, email))
