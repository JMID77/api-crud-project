package com.api.crud.apiCrudProject.infrastructure.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.UserSystem;


@Repository
@Qualifier("userSysJpaRepo")
public interface JpaUserSystemRepository extends JpaRepository<UserSystem, Long> {
    @Query(value = "SELECT * FROM system_users WHERE user_name = :userSys", nativeQuery = true)
    Optional<UserSystem> findByUsername(@Param("userSys") String userSys);
}
