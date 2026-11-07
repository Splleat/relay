package me.splleat.relay.core.domain.space

import jakarta.persistence.EntityManager
import me.splleat.relay.core.domain.user.User
import me.splleat.relay.core.support.RepositoryTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RepositoryTest
class SpaceMemberRepositoryTest {

    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var repository: SpaceMemberRepository

    @Test
    fun `사용자가 속한 스페이스 아이디 목록을 가져온다`() {
        // given
        val user1 = User.register("테스트1", "test1@test.com", "password").also(entityManager::persist)
        val space1 = Space.createShared("스페이스1").also(entityManager::persist)
        val space2 = Space.createShared("스페이스2").also(entityManager::persist)
        val user2 = User.register("테스트2", "test2@test.com", "password").also(entityManager::persist)
        val space3 = Space.createShared("스페이스3").also(entityManager::persist)

        repository.save(SpaceMember.createOwner(user1.requireId(), space1.requireId()))
        repository.save(SpaceMember.createMember(user1.requireId(), space2.requireId()))
        repository.save(SpaceMember.createOwner(user2.requireId(), space3.requireId()))

        // when
        val result = repository.findAllSpaceIdByUserId(user1.requireId())

        assertThat(result)
            .hasSize(2)
            .containsExactlyInAnyOrder(space1.id, space2.id)
    }
}
