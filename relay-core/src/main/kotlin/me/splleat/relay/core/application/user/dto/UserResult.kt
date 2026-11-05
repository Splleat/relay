package me.splleat.relay.core.application.user.dto

data class UserResult(
    val name: String,
    val email: String,
    val imageUrl: String?,
    val statusMessage: String?,
)
