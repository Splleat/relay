package me.splleat.relay.core.domain.channel

import io.mockk.bdd.given
import io.mockk.mockk
import me.splleat.relay.core.common.BusinessException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ChannelMemberServiceTest {
    val repository = mockk<ChannelMemberRepository>()
    val service = ChannelMemberService(repository)

    @Test
    fun `이미 등록된 사용자를 채널 사용자로 등록하면 예외가 발생한다`() {
        // given
        val userId = 1L
        val channelId = 1L

        given { repository.existsByUserIdAndChannelId(userId, channelId) } returns true

        // when
        val exception = assertThrows<BusinessException> { service.register(userId, channelId) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(ChannelErrorCode.CONFLICT)
    }

}
