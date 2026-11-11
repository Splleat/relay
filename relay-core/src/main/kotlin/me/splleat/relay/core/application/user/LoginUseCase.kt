package me.splleat.relay.core.application.user

import jakarta.transaction.Transactional
import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.user.dto.LoginCommand
import me.splleat.relay.core.domain.user.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@UseCase
class LoginUseCase(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun execute(command: LoginCommand): Long {
        val user = userService.getUserForLogin(command.email)

        user.login(command.password, passwordEncoder::matches, LocalDateTime.now())

        return user.requireId()
    }
}
