package me.splleat.relay.core.application.message

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.message.dto.ChannelMessagesResult
import me.splleat.relay.core.application.message.dto.GetChannelMessagesCommand
import me.splleat.relay.core.application.message.dto.MessageCursor
import me.splleat.relay.core.domain.channel.ChannelMemberService
import me.splleat.relay.core.domain.message.MessageService
import org.springframework.transaction.annotation.Transactional

@UseCase
class GetChannelMessagesUseCase(
    private val channelMemberService: ChannelMemberService,
    private val messageService: MessageService
) {

    @Transactional(readOnly = true)
    fun execute(command: GetChannelMessagesCommand): ChannelMessagesResult {
        channelMemberService.validateChannelMember(command.userId, command.channelId)

        val messageSlice = when (command.cursor) {
            MessageCursor.Newest -> messageService.getNewestMessages(command.channelId, command.limit)
            is MessageCursor.Before -> messageService.getMessagesBeforeCursor(command.channelId, command.cursor.messageId, command.limit)
            is MessageCursor.After -> messageService.getMessagesAfterCursor(command.channelId, command.cursor.messageId, command.limit)
        }

        return ChannelMessagesResult.of(messageSlice, command.cursor)
    }
}
