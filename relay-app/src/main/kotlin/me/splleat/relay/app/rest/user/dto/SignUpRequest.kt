package me.splleat.relay.app.rest.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import me.splleat.relay.core.application.user.dto.SignUpCommand

data class SignUpRequest(

    @field:NotBlank
    @field:Size(max = 50)
    val name: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    @field:Size(min = 8, max = 64)
    val password: String,
) {
    fun toCommand(): SignUpCommand =
        SignUpCommand(name = name, email = email, rawPassword = password)
}
