package me.splleat.relay.core.application.user

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.user.dto.SignUpCommand
import me.splleat.relay.core.common.hash
import me.splleat.relay.core.domain.space.SpaceMemberService
import me.splleat.relay.core.domain.space.SpaceService
import me.splleat.relay.core.domain.user.User
import me.splleat.relay.core.domain.user.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

@UseCase
class SignUpUseCase(
    private val userService: UserService,
    private val spaceService: SpaceService,
    private val spaceMemberService: SpaceMemberService,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun execute(command: SignUpCommand): Long {
        val user: User = User.register(command.name, command.email, passwordEncoder.hash(command.rawPassword))
        val userId = userService.register(user).requireId()

        val userSpace = spaceService.registerPersonal()
        spaceMemberService.registerOwner(userId, userSpace.requireId())

        return userId
    }
}
