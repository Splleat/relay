package me.splleat.relay.app.common

data class ApiErrorResponse(
    val code: String,
    val message: String,
    val errors: List<FieldErrorDetail> = emptyList(),
)

data class FieldErrorDetail(
    val field: String,
    val message: String,
)
