package me.splleat.relay.core.domain.space

import me.splleat.relay.core.common.ErrorCode
import me.splleat.relay.core.common.ErrorType

enum class SpaceMemberErrorCode(
    override val type: ErrorType,
    override val message: String,
) : ErrorCode {

    ALREADY_EXISTS(ErrorType.CONFLICT, "이미 스페이스에 존재하는 사용자입니다.")
    ;

    override val code: String
        get() = "SPACE_MEMBER_$name"
}
