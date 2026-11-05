package me.splleat.relay.app.common

import me.splleat.relay.core.common.ErrorCode
import me.splleat.relay.core.common.ErrorType

data class ApiErrorResponse(
    val code: String,
    val message: String,
    val errors: List<FieldErrorDetail> = emptyList(),
) {
    companion object {
        fun from(errorCode: ErrorCode) =
            ApiErrorResponse(errorCode.code, errorCode.message)
    }
}

data class FieldErrorDetail(
    val field: String,
    val message: String,
)
