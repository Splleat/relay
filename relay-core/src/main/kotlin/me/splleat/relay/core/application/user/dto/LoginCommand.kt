package me.splleat.relay.core.application.user.dto

data class LoginCommand(
    val email: String,
    val password: String,
)
