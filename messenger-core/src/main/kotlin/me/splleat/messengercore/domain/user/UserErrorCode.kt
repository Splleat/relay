package me.splleat.messengercore.domain.user

import me.splleat.messengercore.common.ErrorCode
import me.splleat.messengercore.common.ErrorType

enum class UserErrorCode(
    override val type: ErrorType,
    override val message: String
) : ErrorCode {
    NOT_FOUND(ErrorType.NOT_FOUND, "존재하지 않는 사용자입니다."),
    EMAIL_DUPLICATED(ErrorType.CONFLICT, "이미 사용 중인 이메일입니다."),
    EMAIL_MISMATCH(ErrorType.UNAUTHORIZED, "아이디나 비밀번호가 일치하지 않습니다."),
    PASSWORD_MISMATCH(ErrorType.UNAUTHORIZED, "아이디나 비밀번호가 일치하지 않습니다."),
    DEACTIVATED(ErrorType.FORBIDDEN, "비활성화된 사용자입니다.")
}