package com.api.crud.apiCrudProject.domain.repository;

import java.util.List;

import com.api.crud.apiCrudProject.domain.entity.Sandbox;

public interface SandboxRepository {
    Sandbox persist(Sandbox sandbox);
    List<Sandbox> searchAll();
    void removeAll();
    
    List<Sandbox> searchByName(String name);
}
