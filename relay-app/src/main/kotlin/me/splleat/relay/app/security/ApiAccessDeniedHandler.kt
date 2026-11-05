package me.splleat.relay.app.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import tools.jackson.databind.json.JsonMapper

class ApiAccessDeniedHandler(
    private val jsonMapper: JsonMapper
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        val errorCode = AuthErrorCode.ACCESS_DENIED
        response.writeError(errorCode, jsonMapper)
    }
}
