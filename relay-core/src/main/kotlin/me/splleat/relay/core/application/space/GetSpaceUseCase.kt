package me.splleat.relay.core.application.space

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.space.dto.SpaceResult
import me.splleat.relay.core.domain.space.SpaceMemberService
import me.splleat.relay.core.domain.space.SpaceService
import org.springframework.transaction.annotation.Transactional

@UseCase
class GetSpaceUseCase(
    private val spaceService: SpaceService,
    private val spaceMemberService: SpaceMemberService,
) {

    @Transactional(readOnly = true)
    fun execute(userId: Long, spaceId: Long): SpaceResult {
        spaceMemberService.getSpaceMember(userId, spaceId)

        val space = spaceService.getSpace(spaceId)

        // TODO: 채널 등 스페이스 관련 필드 추가

        return SpaceResult.from(space)
    }
}
