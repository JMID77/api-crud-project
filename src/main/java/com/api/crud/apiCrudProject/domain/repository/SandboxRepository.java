package com.api.crud.apiCrudProject.domain.repository;

import java.util.List;

import com.api.crud.apiCrudProject.domain.entity.Sandbox;

public interface SandboxRepository {
    Sandbox save(Sandbox sandbox);
    List<Sandbox> retrieveAll();
    void deleteAll();
    
    List<Sandbox> searchByName(String name);
}
