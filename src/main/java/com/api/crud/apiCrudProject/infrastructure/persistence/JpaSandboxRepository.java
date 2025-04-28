package com.api.crud.apiCrudProject.infrastructure.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.Sandbox;

@Repository
@Qualifier("sandboxJpaRepo")
public interface JpaSandboxRepository extends JpaRepository<Sandbox, Long> {
    @Query(value = "SELECT * FROM sandbox WHERE name = :name", nativeQuery = true)
    List<Sandbox> findByName(@Param("name") String name);
}