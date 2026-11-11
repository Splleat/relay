package me.splleat.relay.core.fixture

import me.splleat.relay.core.domain.space.Space
import me.splleat.relay.core.domain.space.SpaceRepository

fun createSharedSpace(
    name: String = "공유 스페이스",
): Space = Space.createShared(name)

fun SpaceRepository.saveSharedSpace(
    name: String = "공유 스페이스",
): Space = save(createSharedSpace(name))
