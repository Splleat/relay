package me.splleat.relay.core.application.channel

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.channel.dto.CreateSpaceChannelCommand
import me.splleat.relay.core.domain.channel.ChannelMemberService
import me.splleat.relay.core.domain.channel.ChannelService
import me.splleat.relay.core.domain.space.SpaceMemberService
import org.springframework.transaction.annotation.Transactional

@UseCase
class CreateSpaceChannelUseCase(
    private val spaceMemberService: SpaceMemberService,
    private val channelService: ChannelService,
    private val channelMemberService: ChannelMemberService,
) {

    @Transactional
    fun execute(command: CreateSpaceChannelCommand): Long {
        val (userId, spaceId) = command.userId to command.spaceId

        val spaceMember = spaceMemberService.getSpaceMember(userId, spaceId)

        spaceMember.validateOwner()

        val channel = channelService.registerSpace(spaceId, command.channelName)
        val spaceUserIds = spaceMemberService.getAllUserIds(spaceId)

        channelMemberService.registerAll(spaceUserIds, channel.requireId())

        return channel.requireId()
    }
}
