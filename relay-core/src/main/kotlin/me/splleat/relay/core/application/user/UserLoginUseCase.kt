package me.splleat.relay.core.application.user

import jakarta.transaction.Transactional
import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.user.dto.UserLoginCommand
import me.splleat.relay.core.domain.user.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@UseCase
class UserLoginUseCase(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun execute(command: UserLoginCommand): Long {
        val user = userService.getUserForLogin(command.email)

        user.login(command.password, passwordEncoder::matches, LocalDateTime.now())

        return user.requireId()
    }
}