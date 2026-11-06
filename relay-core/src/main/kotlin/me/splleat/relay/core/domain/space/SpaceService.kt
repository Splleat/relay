package me.splleat.relay.core.domain.space

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpaceService(
    private val spaceRepository: SpaceRepository
) {

    @Transactional
    fun registerPersonal(): Space {
        val personalSpace = Space.createPersonal()

        return spaceRepository.save(personalSpace)
    }
}
