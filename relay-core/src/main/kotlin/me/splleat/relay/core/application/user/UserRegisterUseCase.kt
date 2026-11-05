package me.splleat.relay.core.application.user

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.user.dto.UserRegisterCommand
import me.splleat.relay.core.common.hash
import me.splleat.relay.core.domain.user.User
import me.splleat.relay.core.domain.user.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

@UseCase
class UserRegisterUseCase(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun execute(command: UserRegisterCommand): Long {
        val user: User = User.register(command.name, command.email, passwordEncoder.hash(command.rawPassword))

        return userService.register(user).requireId()
    }
}