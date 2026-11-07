package me.splleat.relay.core.application.channel

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.channel.dto.DirectChannelGetOrCreateCommand
import me.splleat.relay.core.domain.channel.ChannelMemberService
import me.splleat.relay.core.domain.channel.ChannelService
import me.splleat.relay.core.domain.space.SpaceMemberService
import org.springframework.transaction.annotation.Transactional

@UseCase
class DirectChannelGetOrCreateUseCase(
    private val spaceMemberService: SpaceMemberService,
    private val channelService: ChannelService,
    private val channelMemberService: ChannelMemberService,
) {

    @Transactional
    fun execute(command: DirectChannelGetOrCreateCommand): Long {
        val (userId, targetId) = command.userId to command.targetId

        val channel = channelService.findDirect(userId, targetId)
            ?: run {
                if (userId != targetId) {
                    spaceMemberService.validShareSpace(userId, targetId)
                }

                val directChannel = channelService.registerDirect(userId, targetId)

                channelMemberService.register(userId, directChannel.channelId)

                if (userId != targetId) {
                    channelMemberService.register(targetId, directChannel.channelId)
                }

                directChannel
            }

        return channel.channelId
    }
}
