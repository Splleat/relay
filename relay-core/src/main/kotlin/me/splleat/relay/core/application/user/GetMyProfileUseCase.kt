package me.splleat.relay.core.application.user

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.user.dto.UserResult
import me.splleat.relay.core.domain.user.UserService
import org.springframework.transaction.annotation.Transactional

@UseCase
class GetMyProfileUseCase(
    private val userService: UserService
) {

    @Transactional(readOnly = true)
    fun execute(userId: Long): UserResult {
        val user = userService.getUser(userId)

        return UserResult(user.name, user.email, user.imageUrl, user.statusMessage)
    }
}
