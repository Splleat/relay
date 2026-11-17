package me.splleat.relay.core.application.space

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.space.dto.SpaceResult
import me.splleat.relay.core.domain.channel.ChannelService
import me.splleat.relay.core.domain.space.SpaceMemberService
import me.splleat.relay.core.domain.space.SpaceService
import org.springframework.transaction.annotation.Transactional

@UseCase
class GetSpaceUseCase(
    private val spaceService: SpaceService,
    private val spaceMemberService: SpaceMemberService,
    private val channelService: ChannelService,
) {

    @Transactional(readOnly = true)
    fun execute(userId: Long, spaceId: Long): SpaceResult {
        spaceMemberService.getSpaceMember(userId, spaceId)

        val space = spaceService.getSpace(spaceId)

        val channels = channelService.getSpaceChannels(spaceId)

        return SpaceResult.from(space, channels)
    }
}
