package com.api.crud.apiCrudProject.infrastructure.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.UserSystem;


@Repository
@Qualifier("userSysJpaRepo")
public interface JpaUserSystemRepository extends JpaRepository<UserSystem, Long>  {
    Optional<UserSystem> findByUsername(String username);
    // @Query(value = "SELECT u FROM UserSytem u WHERE u.username = :username", nativeQuery = true)
    // // @Query(value = "SELECT * FROM system_users WHERE user_name = :username", nativeQuery = true)
    // Optional<UserSystem> findByUsername(@Param("username") String username);
}
