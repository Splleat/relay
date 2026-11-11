package me.splleat.relay.app.security

import io.kotest.matchers.shouldBe
import io.mockk.bdd.given
import io.mockk.mockk
import me.splleat.relay.core.domain.channel.ChannelMemberService
import org.junit.jupiter.api.Test
import org.springframework.messaging.support.GenericMessage
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.messaging.access.intercept.MessageAuthorizationContext
import java.util.function.Supplier

class ChannelMemberAuthorizationTest {
    private val channelMemberService = mockk<ChannelMemberService>()
    private val channelMemberAuthorization = ChannelMemberAuthorization(channelMemberService)

    @Test
    fun `인증 정보가 없으면 false를 반환한다`() {
        // given
        val authorization = Supplier<Authentication?> { null }
        val context = MessageAuthorizationContext(GenericMessage("test"), mapOf("channel-id" to "1"))

        // when
        val decision = channelMemberAuthorization.authorize(authorization, context)

        // then
        decision.isGranted shouldBe false
    }

    @Test
    fun `채널 아이디가 없으면 false를 반환한다`() {
        // given
        val authorization = Supplier<Authentication?> { UsernamePasswordAuthenticationToken(1L, null) }
        val context = MessageAuthorizationContext(GenericMessage("test"))

        // when
        val decision = channelMemberAuthorization.authorize(authorization, context)

        // then
        decision.isGranted shouldBe false
    }

    @Test
    fun `채널에 속한 사용자가 아니라면 false를 반환한다`() {
        // given
        val userId = 1L
        val channelId = 1L
        val authorization = Supplier<Authentication?> { UsernamePasswordAuthenticationToken(userId, null) }
        val context = MessageAuthorizationContext(GenericMessage("test"), mapOf("channel-id" to channelId.toString()))

        given { channelMemberService.isChannelMember(userId, channelId) } returns false

        // when
        val decision = channelMemberAuthorization.authorize(authorization, context)

        // then
        decision.isGranted shouldBe false
    }

    @Test
    fun `채널에 속한 사용자라면 true를 반환한다`() {
        // given
        val userId = 1L
        val channelId = 1L
        val authorization = Supplier<Authentication?> { UsernamePasswordAuthenticationToken(userId, null) }
        val context = MessageAuthorizationContext(GenericMessage("test"), mapOf("channel-id" to channelId.toString()))

        given { channelMemberService.isChannelMember(userId, channelId) } returns true

        // when
        val decision = channelMemberAuthorization.authorize(authorization, context)

        // then
        decision.isGranted shouldBe true
    }
}
