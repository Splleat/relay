package me.splleat.relay.app.rest.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import me.splleat.relay.core.application.user.dto.LoginCommand

data class LoginRequest(

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String,
) {
    fun toCommand() =
        LoginCommand(email = email, password = password)
}
