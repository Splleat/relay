package me.splleat.relay.app.rest.auth.dto

import org.springframework.security.web.csrf.CsrfToken

data class CsrfTokenResponse(
    val token: String,
) {
    companion object {
        fun from(csrfToken: CsrfToken) =
            CsrfTokenResponse(csrfToken.token)
    }
}
