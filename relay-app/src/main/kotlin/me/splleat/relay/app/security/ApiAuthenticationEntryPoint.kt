package me.splleat.relay.app.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import tools.jackson.databind.json.JsonMapper

class ApiAuthenticationEntryPoint(
    private val jsonMapper: JsonMapper
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val errorCode = AuthErrorCode.UNAUTHENTICATED
        response.writeError(errorCode, jsonMapper)
    }
}