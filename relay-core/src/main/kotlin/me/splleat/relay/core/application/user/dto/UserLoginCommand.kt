package me.splleat.relay.core.application.user.dto

data class UserLoginCommand(
    val email: String,
    val password: String,
)