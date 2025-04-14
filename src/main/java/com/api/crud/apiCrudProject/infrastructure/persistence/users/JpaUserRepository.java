package com.api.crud.apiCrudProject.infrastructure.persistence.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.users.User;
import com.api.crud.apiCrudProject.domain.repository.users.UserRepository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

}
