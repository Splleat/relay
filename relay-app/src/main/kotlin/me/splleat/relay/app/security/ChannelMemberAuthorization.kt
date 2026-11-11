package me.splleat.relay.app.security

import me.splleat.relay.core.domain.channel.ChannelMemberService
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.authorization.AuthorizationResult
import org.springframework.security.core.Authentication
import org.springframework.security.messaging.access.intercept.MessageAuthorizationContext
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class ChannelMemberAuthorization(
    private val channelMemberService: ChannelMemberService
) : AuthorizationManager<MessageAuthorizationContext<*>> {

    override fun authorize(
        authentication: Supplier<out Authentication?>,
        context: MessageAuthorizationContext<*>,
    ): AuthorizationResult {
        val userId: Long = authentication.get()?.principal.toString().toLongOrNull()
            ?: return AuthorizationDecision(false)
        val channelId: Long = context.variables["channel-id"]?.toLongOrNull()
            ?: return AuthorizationDecision(false)

        val granted = channelMemberService.isChannelMember(userId, channelId)

        return AuthorizationDecision(granted)
    }
}
