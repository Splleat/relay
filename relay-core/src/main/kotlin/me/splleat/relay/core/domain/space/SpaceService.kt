package me.splleat.relay.core.domain.space

import me.splleat.relay.core.common.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpaceService(
    private val repository: SpaceRepository
) {

    @Transactional
    fun registerPersonal(): Space {
        val personalSpace = Space.createPersonal()

        return repository.save(personalSpace)
    }

    @Transactional
    fun registerShared(name: String): Space {
        val sharedSpace = Space.createShared(name)

        return repository.save(sharedSpace)
    }

    @Transactional(readOnly = true)
    fun getSpace(id: Long): Space {
        return repository.findByIdOrNull(id)
            ?: throw BusinessException(SpaceErrorCode.NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun getSpaces(ids: List<Long>): List<Space> {
        if (ids.isEmpty()) {
            return emptyList()
        }

        return repository.findAllById(ids)
    }
}
