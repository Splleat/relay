package me.splleat.relay.app.rest.space

import jakarta.validation.Valid
import me.splleat.relay.app.rest.createLocation
import me.splleat.relay.app.rest.space.dto.SpaceCreateRequest
import me.splleat.relay.app.rest.space.dto.SpaceListResponse
import me.splleat.relay.app.rest.space.dto.SpaceResponse
import me.splleat.relay.core.application.space.SpaceCreateUseCase
import me.splleat.relay.core.application.space.SpaceGetUseCase
import me.splleat.relay.core.application.space.SpaceListGetUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class SpaceController(
    private val spaceCreateUseCase: SpaceCreateUseCase,
    private val spaceListGetUseCase: SpaceListGetUseCase,
    private val spaceGetUseCase: SpaceGetUseCase,
) {

    @PostMapping("/spaces")
    fun createSpace(
        @RequestBody @Valid request: SpaceCreateRequest,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<Unit> {
        val spaceId = spaceCreateUseCase.execute(request.toCommand(userId))
        val location = createLocation(spaceId)

        return ResponseEntity.created(location).build()
    }

    @GetMapping("/spaces")
    fun getAllMySpace(@AuthenticationPrincipal userId: Long): ResponseEntity<List<SpaceListResponse>> {
        val result = spaceListGetUseCase.execute(userId)

        return ResponseEntity.ok(result.map(SpaceListResponse::from))
    }

    @GetMapping("/spaces/{id}")
    fun getSpace(
        @PathVariable id: Long,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<SpaceResponse> {
        val result = spaceGetUseCase.execute(userId, id)

        return ResponseEntity.ok(SpaceResponse.from(result))
    }
}
