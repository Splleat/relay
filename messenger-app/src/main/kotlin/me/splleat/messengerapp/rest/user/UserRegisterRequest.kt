package me.splleat.messengerapp.rest.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import me.splleat.messengercore.application.user.UserRegisterCommand

data class UserRegisterRequest(

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
    fun toCommand(): UserRegisterCommand =
        UserRegisterCommand(name = name, email = email, rawPassword = password)
}