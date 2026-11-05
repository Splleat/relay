package me.splleat.relay.app.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Service

@Service
class SessionService {

    private val securityContextRepository = HttpSessionSecurityContextRepository()

    fun saveSession(userId: Long, request: HttpServletRequest, response: HttpServletResponse) {
        request.getSession(false)?.invalidate()

        val authentication = UsernamePasswordAuthenticationToken.authenticated(userId, null, emptyList())
        val context = SecurityContextHolder.createEmptyContext().apply { this.authentication = authentication }
        SecurityContextHolder.setContext(context)
        securityContextRepository.saveContext(context, request, response)
    }
}