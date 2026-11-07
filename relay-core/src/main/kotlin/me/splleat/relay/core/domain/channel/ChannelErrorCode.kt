package me.splleat.relay.core.domain.channel

import me.splleat.relay.core.common.ErrorCode
import me.splleat.relay.core.common.ErrorType

enum class ChannelErrorCode(
    override val type: ErrorType,
    override val message: String,
) : ErrorCode {
    NOT_FOUND(ErrorType.NOT_FOUND, "존재하지 않는 채널입니다."),
    CONFLICT(ErrorType.CONFLICT, "이미 채널에 소속되어 있습니다.")
    ;

    override val code: String
        get() = "CHANNEL_$name"
}
