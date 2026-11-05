package me.splleat.relay.core.domain.user

import me.splleat.relay.core.common.ErrorCode
import me.splleat.relay.core.common.ErrorType

enum class UserErrorCode(
    override val type: ErrorType,
    override val message: String,
) : ErrorCode {
    NOT_FOUND(ErrorType.NOT_FOUND, "존재하지 않는 사용자입니다."),
    EMAIL_DUPLICATED(ErrorType.CONFLICT, "이미 사용 중인 이메일입니다."),
    INVALID_CREDENTIALS(ErrorType.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."),
    DEACTIVATED(ErrorType.FORBIDDEN, "비활성화된 사용자입니다.");

    override val code: String
        get() = "USER_$name"
}