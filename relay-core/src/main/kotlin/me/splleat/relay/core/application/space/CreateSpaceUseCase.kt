package me.splleat.relay.core.application.space

import me.splleat.relay.core.application.UseCase
import me.splleat.relay.core.application.space.dto.CreateSpaceCommand
import me.splleat.relay.core.domain.space.SpaceMemberService
import me.splleat.relay.core.domain.space.SpaceService
import org.springframework.transaction.annotation.Transactional

@UseCase
class CreateSpaceUseCase(
    private val spaceService: SpaceService,
    private val spaceMemberService: SpaceMemberService,
) {

    @Transactional
    fun execute(command: CreateSpaceCommand): Long {
        val space = spaceService.registerShared(command.name)
        val spaceId = space.requireId()

        spaceMemberService.registerOwner(command.userId, spaceId)

        return spaceId
    }
}
