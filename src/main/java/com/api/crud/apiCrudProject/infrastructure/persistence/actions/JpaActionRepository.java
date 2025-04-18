package com.api.crud.apiCrudProject.infrastructure.persistence.actions;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.actions.Action;
import com.api.crud.apiCrudProject.domain.repository.actions.ActionRepository;

@Repository
public interface JpaActionRepository extends JpaRepository<Action, Long>, ActionRepository {
    @Query(value = "SELECT * FROM actions WHERE action_status = :actionStatus", nativeQuery = true)
    List<Action> findActionStatusByStatus(@Param("actionStatus") String actionStatus);
}