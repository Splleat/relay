package me.splleat.relay.core.application.message

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import me.splleat.relay.core.application.message.dto.MessageCreatedEvent
import me.splleat.relay.core.application.message.dto.SendMessageCommand
import me.splleat.relay.core.common.BusinessException
import me.splleat.relay.core.domain.channel.ChannelErrorCode
import me.splleat.relay.core.domain.channel.ChannelMemberRepository
import me.splleat.relay.core.domain.channel.ChannelRepository
import me.splleat.relay.core.domain.message.MessageRepository
import me.splleat.relay.core.domain.user.UserRepository
import me.splleat.relay.core.fixture.saveChannelMember
import me.splleat.relay.core.fixture.saveDirectChannel
import me.splleat.relay.core.fixture.saveUser
import me.splleat.relay.core.support.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.event.ApplicationEvents
import org.springframework.test.context.event.RecordApplicationEvents
import java.util.*

@IntegrationTest
@RecordApplicationEvents
class SendMessageUseCaseTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var channelRepository: ChannelRepository

    @Autowired
    private lateinit var channelMemberRepository: ChannelMemberRepository

    @Autowired
    private lateinit var messageRepository: MessageRepository

    @Autowired
    private lateinit var useCase: SendMessageUseCase

    @Test
    fun `새로운 메시지라면 저장되고 isCreated가 true이며, 이벤트가 발행된다`(events: ApplicationEvents) {
        // given
        val user = userRepository.saveUser()

        val channel = channelRepository.saveDirectChannel()

        channelMemberRepository.saveChannelMember(user.requireId(), channel.requireId())

        val command = SendMessageCommand(
            user.requireId(),
            channel.requireId(),
            "테스트 메시지",
            UUID.randomUUID()
        )

        // when
        val result = useCase.execute(command)

        // then
        result.isCreated shouldBe true

        val message = messageRepository.findByUserIdAndIdempotencyKey(command.userId, command.idempotencyKey)

        message.shouldNotBeNull()

        val event = events.stream(MessageCreatedEvent::class.java).toList()

        event shouldHaveSize 1
    }

    @Test
    fun `같은 키로 두 번 보내면 하나만 저장되고, 두 번째 응답은 isCreated = false이다`(events: ApplicationEvents) {
        // given
        val user = userRepository.saveUser()
        val channel = channelRepository.saveDirectChannel()
        channelMemberRepository.saveChannelMember(user.requireId(), channel.requireId())

        val command = SendMessageCommand(
            user.requireId(),
            channel.requireId(),
            "테스트 메시지",
            UUID.randomUUID()
        )

        // when
        val result1 = useCase.execute(command)
        val result2 = useCase.execute(command)

        result1.isCreated shouldBe true
        result2.isCreated shouldBe false

        result1.message.messageId shouldBe result2.message.messageId

        messageRepository.count() shouldBe 1

        val event = events.stream(MessageCreatedEvent::class.java).toList()

        event shouldHaveSize 1
    }

    @Test
    fun `채널에 속하지 않은 사용자가 메시지를 보내면 예외가 발생하고 메시지가 저장되지 않는다`(events: ApplicationEvents) {
        // given
        val user = userRepository.saveUser()
        val channel = channelRepository.saveDirectChannel()

        val command = SendMessageCommand(
            user.requireId(),
            channel.requireId(),
            "테스트 메시지",
            UUID.randomUUID()
        )

        // when
        val exception = shouldThrow<BusinessException> { useCase.execute(command) }

        exception.errorCode shouldBe ChannelErrorCode.NOT_FOUND

        messageRepository.count() shouldBe 0

        val event = events.stream(MessageCreatedEvent::class.java).toList()

        event shouldHaveSize 0
    }
}
