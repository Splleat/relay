package me.splleat.relay.app.rest.space

import jakarta.validation.Valid
import me.splleat.relay.app.rest.createLocation
import me.splleat.relay.app.rest.space.dto.CreateSpaceRequest
import me.splleat.relay.app.rest.space.dto.JoinedSpacesResponse
import me.splleat.relay.app.rest.space.dto.SpaceResponse
import me.splleat.relay.core.application.space.CreateSpaceUseCase
import me.splleat.relay.core.application.space.GetSpaceUseCase
import me.splleat.relay.core.application.space.GetJoinedSpacesUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class SpaceController(
    private val createSpaceUseCase: CreateSpaceUseCase,
    private val getJoinedSpacesUseCase: GetJoinedSpacesUseCase,
    private val getSpaceUseCase: GetSpaceUseCase,
) {

    @PostMapping("/spaces")
    fun createSpace(
        @RequestBody @Valid request: CreateSpaceRequest,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<Unit> {
        val spaceId = createSpaceUseCase.execute(request.toCommand(userId))
        val location = createLocation(spaceId)

        return ResponseEntity.created(location).build()
    }

    @GetMapping("/spaces")
    fun getJoinedSpaces(@AuthenticationPrincipal userId: Long): ResponseEntity<JoinedSpacesResponse> {
        val result = getJoinedSpacesUseCase.execute(userId)

        return ResponseEntity.ok(JoinedSpacesResponse.from(result))
    }

    @GetMapping("/spaces/{id}")
    fun getSpace(
        @PathVariable id: Long,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<SpaceResponse> {
        val result = getSpaceUseCase.execute(userId, id)

        return ResponseEntity.ok(SpaceResponse.from(result))
    }
}
