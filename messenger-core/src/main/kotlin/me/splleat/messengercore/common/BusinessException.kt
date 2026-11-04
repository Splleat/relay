package me.splleat.messengercore.common

class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message, null, false, false)

interface ErrorCode {
    val type: ErrorType
    val message: String
}

enum class ErrorType { BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, CONFLICT }