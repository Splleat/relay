package me.splleat.relay.core.application.message

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.message.dto.MessageCreatedEvent
import me.splleat.relay.core.application.message.dto.MessageResult
import me.splleat.relay.core.application.message.dto.MessageSendCommand
import me.splleat.relay.core.application.message.dto.MessageSendResult
import me.splleat.relay.core.domain.channel.ChannelMemberService
import me.splleat.relay.core.domain.message.MessageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.transaction.annotation.Transactional

@UseCase
class MessageSendUseCase(
    private val channelMemberService: ChannelMemberService,
    private val messageService: MessageService,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun execute(command: MessageSendCommand): MessageSendResult {
        channelMemberService.validateChannelMember(command.userId, command.channelId)

        val existing = messageService.findMessage(command.userId, command.idempotencyKey)

        if (existing != null)
            return MessageSendResult(false, MessageResult.from(existing))

        val created = messageService.register(command.toEntity())

        val result = MessageResult.from(created)

        eventPublisher.publishEvent(MessageCreatedEvent.from(result))

        return MessageSendResult(true, result)
    }
}
