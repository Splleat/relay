package me.splleat.relay.core.application.user

data class UserRegisterCommand(
    val name: String,
    val email: String,
    val rawPassword: String
)
