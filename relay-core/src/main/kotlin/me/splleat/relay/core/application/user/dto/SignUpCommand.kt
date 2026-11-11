package me.splleat.relay.core.application.user.dto

data class SignUpCommand(
    val name: String,
    val email: String,
    val rawPassword: String
)
