package com.api.crud.apiCrudProject.infrastructure.persistence.actions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.actions.Action;
import com.api.crud.apiCrudProject.domain.repository.actions.ActionRepository;

@Repository
public interface JpaActionRepository extends JpaRepository<Action, Long>, ActionRepository {

}
