package me.splleat.relay.core.domain.user

import me.splleat.relay.core.common.BusinessException
import org.springframework.data.repository.findByIdOrNull
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

    @Transactional(readOnly = true)
    fun getUserForLogin(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw BusinessException(UserErrorCode.INVALID_CREDENTIALS)
    }

    @Transactional(readOnly = true)
    fun getUser(userId: Long): User {
        return userRepository.findByIdOrNull(userId)
            ?: throw BusinessException(UserErrorCode.NOT_FOUND)
    }
}
