package me.splleat.relay.app.security

import jakarta.servlet.http.HttpServletResponse
import me.splleat.relay.app.common.ApiErrorResponse
import tools.jackson.databind.json.JsonMapper

fun HttpServletResponse.writeError(errorCode: AuthErrorCode, jsonMapper: JsonMapper) {
    val errorResponse = ApiErrorResponse.from(errorCode)

    this.status = errorCode.type.status
    this.contentType = "application/json"
    this.characterEncoding = "UTF-8"

    jsonMapper.writeValue(this.writer, errorResponse)
}