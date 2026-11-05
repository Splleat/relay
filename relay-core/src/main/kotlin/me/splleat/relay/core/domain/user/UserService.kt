package me.splleat.relay.core.domain.user

import me.splleat.relay.core.common.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun register(user: User): User {
        if (userRepository.existsByEmail(user.email)) {
            throw BusinessException(UserErrorCode.EMAIL_DUPLICATED)
        }

        return userRepository.save(user)
    }
}