package me.splleat.relay.core.application.space

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.space.dto.JoinedSpacesResult
import me.splleat.relay.core.application.space.dto.SpaceSummaryResult
import me.splleat.relay.core.domain.space.SpaceMemberService
import me.splleat.relay.core.domain.space.SpaceService
import org.springframework.transaction.annotation.Transactional

@UseCase
class GetJoinedSpacesUseCase(
    private val spaceService: SpaceService,
    private val spaceMemberService: SpaceMemberService,
) {

    @Transactional(readOnly = true)
    fun execute(userId: Long): JoinedSpacesResult {
        val spaceIds = spaceMemberService.getAllSpaceIdByUser(userId)
        val spaces = spaceService.getSpaces(spaceIds)

        return JoinedSpacesResult(spaces.map(SpaceSummaryResult::from))
    }
}
