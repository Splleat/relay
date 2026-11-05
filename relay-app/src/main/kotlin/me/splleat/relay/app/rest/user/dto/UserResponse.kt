package me.splleat.relay.app.rest.user.dto

import me.splleat.relay.core.application.user.dto.UserResult

data class UserResponse(
    val name: String,
    val email: String,
    val imageUrl: String?,
    val statusMessage: String?
) {
    companion object {
        fun from(result: UserResult) =
            UserResponse(result.name, result.email, result.imageUrl, result.statusMessage)
    }
}