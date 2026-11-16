package me.splleat.relay.app.rest.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import me.splleat.relay.app.rest.auth.dto.CsrfTokenResponse
import me.splleat.relay.app.rest.auth.dto.LoginRequest
import me.splleat.relay.app.security.SessionService
import me.splleat.relay.core.application.user.LoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val sessionService: SessionService,
    private val loginUseCase: LoginUseCase,
) {

    @PostMapping("/auth/login")
    fun login(
        @RequestBody @Valid request: LoginRequest,
        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse,
    ): ResponseEntity<Unit> {
        val userId = loginUseCase.execute(request.toCommand())

        sessionService.saveSession(userId, httpRequest, httpResponse)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/auth/csrf")
    fun getCsrfToken(csrfToken: CsrfToken): ResponseEntity<CsrfTokenResponse> {
        return ResponseEntity.ok(CsrfTokenResponse.from(csrfToken))
    }
}
