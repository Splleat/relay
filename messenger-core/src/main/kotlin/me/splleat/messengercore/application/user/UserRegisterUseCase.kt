package me.splleat.messengercore.application.user

import me.splleat.messengercore.application.UseCase
import me.splleat.messengercore.common.hash
import me.splleat.messengercore.domain.user.User
import me.splleat.messengercore.domain.user.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

@UseCase
class UserRegisterUseCase(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun execute(command: UserRegisterCommand) {
        val user: User = User.register(command.name, command.email, passwordEncoder.hash(command.rawPassword))

        userService.register(user)
    }
}