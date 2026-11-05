package me.splleat.relay.app.rest.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import me.splleat.relay.app.rest.auth.dto.UserLoginRequest
import me.splleat.relay.app.security.SessionService
import me.splleat.relay.core.application.user.UserLoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val sessionService: SessionService,
    private val userLoginUseCase: UserLoginUseCase,
) {

    @PostMapping("/auth/login")
    fun login(
        @RequestBody @Valid request: UserLoginRequest,
        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse,
    ): ResponseEntity<Void> {
        val userId = userLoginUseCase.execute(request.toCommand())

        sessionService.saveSession(userId, httpRequest, httpResponse)

        return ResponseEntity.noContent().build()
    }
}