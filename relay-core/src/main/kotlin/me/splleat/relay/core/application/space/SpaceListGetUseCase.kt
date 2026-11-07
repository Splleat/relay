package me.splleat.relay.core.application.space

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.space.dto.SpaceListResult
import me.splleat.relay.core.domain.space.SpaceMemberService
import me.splleat.relay.core.domain.space.SpaceService
import org.springframework.transaction.annotation.Transactional

@UseCase
class SpaceListGetUseCase(
    private val spaceService: SpaceService,
    private val spaceMemberService: SpaceMemberService,
) {

    @Transactional(readOnly = true)
    fun execute(userId: Long): List<SpaceListResult> {
        val spaceIds = spaceMemberService.getAllSpaceIdByUser(userId)
        val spaces = spaceService.getSpaces(spaceIds)

        return spaces.map { space -> SpaceListResult.from(space) }
    }
}
