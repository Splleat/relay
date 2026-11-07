package me.splleat.relay.core.domain.space

import me.splleat.relay.core.common.ErrorCode
import me.splleat.relay.core.common.ErrorType

enum class SpaceErrorCode(
    override val type: ErrorType,
    override val message: String,
) : ErrorCode {

    NOT_FOUND(ErrorType.NOT_FOUND, "존재하지 않는 스페이스입니다.")
    ;

    override val code: String
        get() = "SPACE_$name"
}
