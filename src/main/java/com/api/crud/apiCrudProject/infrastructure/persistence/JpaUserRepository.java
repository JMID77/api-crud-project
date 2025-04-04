package com.api.crud.apiCrudProject.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.User;
import com.api.crud.apiCrudProject.domain.repository.UserRepository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

}
