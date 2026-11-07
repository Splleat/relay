package me.splleat.relay.core.domain.space

import me.splleat.relay.core.common.ErrorCode
import me.splleat.relay.core.common.ErrorType

enum class SpaceMemberErrorCode(
    override val type: ErrorType,
    override val message: String,
) : ErrorCode {

    NOT_FOUND(ErrorType.NOT_FOUND, "해당 스페이스 사용자가 아닙니다."),
    FORBIDDEN(ErrorType.FORBIDDEN, "접근 권한이 없습니다.");

    override val code: String
        get() = "SPACE_MEMBER_$name"
}
