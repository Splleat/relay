package me.splleat.relay.app.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager

@Configuration(proxyBeanMethods = false)
@EnableWebSocketSecurity
class WebSocketSecurityConfig(
    private val channelMemberAuthorization: ChannelMemberAuthorization
) {

    @Bean
    fun authorizationManager(messages: MessageMatcherDelegatingAuthorizationManager.Builder): AuthorizationManager<Message<*>> {
        messages
            .simpTypeMatchers(
                SimpMessageType.CONNECT,
                SimpMessageType.DISCONNECT,
                SimpMessageType.UNSUBSCRIBE,
                SimpMessageType.HEARTBEAT
            ).permitAll()
            .simpTypeMatchers(SimpMessageType.MESSAGE).denyAll()
            .simpSubscribeDestMatchers("/sub/channels/{channel-id}").access(channelMemberAuthorization)
            .anyMessage().denyAll()

        return messages.build()
    }
}
