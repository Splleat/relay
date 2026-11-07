package me.splleat.relay.app.rest.user

import jakarta.validation.Valid
import me.splleat.relay.app.rest.createLocation
import me.splleat.relay.app.rest.user.dto.UserRegisterRequest
import me.splleat.relay.app.rest.user.dto.UserResponse
import me.splleat.relay.core.application.user.UserGetMeUseCase
import me.splleat.relay.core.application.user.UserRegisterUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
class UserController(
    private val userRegisterUseCase: UserRegisterUseCase,
    private val userGetMeUseCase: UserGetMeUseCase,
) {

    @PostMapping("/users")
    fun register(@Valid @RequestBody request: UserRegisterRequest): ResponseEntity<Void> {
        val userId = userRegisterUseCase.execute(request.toCommand())
        val location = createLocation(userId)

        return ResponseEntity.created(location)
            .build()
    }

    @GetMapping("/users/me")
    fun me(@AuthenticationPrincipal userId: Long): ResponseEntity<UserResponse> {
        val user = userGetMeUseCase.execute(userId)

        return ResponseEntity.ok(UserResponse.from(user))
    }
}
