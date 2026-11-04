package me.splleat.relay.app.rest.user

import jakarta.validation.Valid
import me.splleat.relay.core.application.user.UserRegisterUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
class UserController(
    private val userRegisterUseCase: UserRegisterUseCase
) {

    @PostMapping("/users")
    fun register(@Valid @RequestBody request: UserRegisterRequest): ResponseEntity<Void> {
        val userId = userRegisterUseCase.execute(request.toCommand())
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userId)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}