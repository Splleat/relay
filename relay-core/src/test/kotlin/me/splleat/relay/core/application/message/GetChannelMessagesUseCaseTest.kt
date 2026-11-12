package me.splleat.relay.core.application.message

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import me.splleat.relay.core.application.message.dto.GetChannelMessagesCommand
import me.splleat.relay.core.application.message.dto.MessageCursor
import me.splleat.relay.core.common.BusinessException
import me.splleat.relay.core.domain.channel.ChannelErrorCode
import me.splleat.relay.core.domain.channel.ChannelRepository
import me.splleat.relay.core.domain.user.UserRepository
import me.splleat.relay.core.fixture.saveDirectChannel
import me.splleat.relay.core.fixture.saveUser
import me.splleat.relay.core.support.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
class GetChannelMessagesUseCaseTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var channelRepository: ChannelRepository

    @Autowired
    private lateinit var useCase: GetChannelMessagesUseCase

    @Test
    fun `채널에 속하지 않은 사용자가 채널 메시지를 조회하면 예외가 발생한다`() {
        // given
        val user = userRepository.saveUser()
        val channel = channelRepository.saveDirectChannel()
        val limit = 10

        val command = GetChannelMessagesCommand(user.requireId(), channel.requireId(), MessageCursor.Newest, limit)

        // when
        val exception = shouldThrow<BusinessException> { useCase.execute(command) }

        exception.errorCode shouldBe ChannelErrorCode.NOT_FOUND
    }
}
