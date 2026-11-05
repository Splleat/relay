package me.splleat.relay.app.security

import me.splleat.relay.core.common.ErrorCode
import me.splleat.relay.core.common.ErrorType

enum class AuthErrorCode(
    override val type: ErrorType,
    override val message: String,
) : ErrorCode {
    UNAUTHENTICATED(ErrorType.UNAUTHORIZED, "인증이 필요합니다."),
    ACCESS_DENIED(ErrorType.FORBIDDEN, "접근 권한이 없습니다.");

    override val code: String
        get() = "AUTH_$name"
}