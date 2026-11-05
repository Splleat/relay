package me.splleat.relay.app.rest.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import me.splleat.relay.core.application.user.dto.UserLoginCommand

data class UserLoginRequest(

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String,
) {
    fun toCommand() =
        UserLoginCommand(email = email, password = password)
}