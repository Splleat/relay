package me.splleat.relay.app.rest

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

fun createLocation(id: Long): URI =
    ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri()

fun createLocation(path: String, id: Long): URI =
    ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(path)
        .buildAndExpand(id)
        .toUri()
