package infrastructure.persistence;

import infrastructure.persistence.entity.UserEntity;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

}
