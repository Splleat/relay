package me.splleat.messengercore.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.messengercore.common.BusinessException
import me.splleat.messengercore.domain.SoftDeletableEntity
import java.time.LocalDateTime

@Entity
@Table(name = "users")
open class User private constructor(
    name: String,
    email: String,
    passwordHash: String,
) : SoftDeletableEntity() {

    var name: String = name
        protected set

    var email: String = email
        protected set

    private var passwordHash: String = passwordHash

    var imageUrl: String? = null
        protected set

    var statusMessage: String? = null
        protected set

    var lastLoginAt: LocalDateTime? = null
        protected set

    companion object {
        fun register(
            name: String,
            email: String,
            passwordHash: String,
        ) = User(name, email, passwordHash)
    }

    fun login(
        rawPassword: String,
        matches: (raw: String, hash: String) -> Boolean,
        at: LocalDateTime,
    ) {
        if (!matches(rawPassword, passwordHash)) {
            throw BusinessException(UserErrorCode.PASSWORD_MISMATCH)
        }

        lastLoginAt = at
    }
}