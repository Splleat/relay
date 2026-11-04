package me.splleat.relay.core.common

class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message, null, false, false)

interface ErrorCode {
    val type: ErrorType
    val code: String
    val message: String
}

enum class ErrorType(val status: Int) {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
}