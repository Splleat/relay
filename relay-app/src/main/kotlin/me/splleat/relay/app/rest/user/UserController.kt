package me.splleat.relay.app.rest.user

import jakarta.validation.Valid
import me.splleat.relay.app.rest.createLocation
import me.splleat.relay.app.rest.user.dto.SignUpRequest
import me.splleat.relay.app.rest.user.dto.UserResponse
import me.splleat.relay.core.application.user.GetMyProfileUseCase
import me.splleat.relay.core.application.user.SignUpUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val signUpUseCase: SignUpUseCase,
    private val getMyProfileUseCase: GetMyProfileUseCase,
) {

    @PostMapping("/users")
    fun signUp(@Valid @RequestBody request: SignUpRequest): ResponseEntity<Unit> {
        val userId = signUpUseCase.execute(request.toCommand())
        val location = createLocation(userId)

        return ResponseEntity.created(location)
            .build()
    }

    @GetMapping("/users/me")
    fun getMyProfile(@AuthenticationPrincipal userId: Long): ResponseEntity<UserResponse> {
        val user = getMyProfileUseCase.execute(userId)

        return ResponseEntity.ok(UserResponse.from(user))
    }
}
