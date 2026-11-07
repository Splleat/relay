package me.splleat.relay.core.application.channel.dto

import me.splleat.relay.core.domain.user.User

data class ChannelMemberResult(
    val userId: Long,
    val name: String,
    val imageUrl: String?,
) {
    companion object {
        fun from(user: User) =
            ChannelMemberResult(user.requireId(), user.name, user.imageUrl)
    }
}
