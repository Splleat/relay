package me.splleat.relay.core.application.channel

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.channel.dto.ChannelResult
import me.splleat.relay.core.application.channel.dto.GetChannelCommand
import me.splleat.relay.core.domain.channel.ChannelMemberService
import me.splleat.relay.core.domain.channel.ChannelService
import me.splleat.relay.core.domain.user.UserService
import org.springframework.transaction.annotation.Transactional

@UseCase
class GetChannelUseCase(
    private val channelService: ChannelService,
    private val channelMemberService: ChannelMemberService,
    private val userService: UserService,
) {

    @Transactional(readOnly = true)
    fun execute(command: GetChannelCommand): ChannelResult {
        channelMemberService.validateChannelMember(command.userId, command.channelId)

        val channel = channelService.getChannel(command.channelId)
        val joinedUserIds = channelMemberService.getChannelMemberUserIds(channel.requireId())
        val channelMembers = userService.getUsers(joinedUserIds)

        return ChannelResult.from(channel, channelMembers)
    }
}
