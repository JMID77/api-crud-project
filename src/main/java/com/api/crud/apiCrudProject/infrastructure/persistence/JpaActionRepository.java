package com.api.crud.apiCrudProject.infrastructure.persistence;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.Action;
@Repository
@Qualifier("actionJpaRepo")
public interface JpaActionRepository extends JpaRepository<Action, Long> {
    @Query(value = "SELECT * FROM actions WHERE action_status = :actionStatus", nativeQuery = true)
    List<Action> findActionStatusByStatus(@Param("actionStatus") String actionStatus);
}