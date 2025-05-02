package com.api.crud.apiCrudProject.infrastructure.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.Sandbox;
import com.api.crud.apiCrudProject.domain.repository.SandboxRepository;

@Repository
@Qualifier("sandboxSpecificRepo")
public class JpaSandBoxRepositoryImpl implements SandboxRepository{
    private final JpaSandboxRepository jpaSandbox;

    public JpaSandBoxRepositoryImpl(@Lazy JpaSandboxRepository jpaSandbox) {
        this.jpaSandbox = jpaSandbox;
    }

    @Override
    public Sandbox persist(Sandbox sandbox) {
        return this.jpaSandbox.save(sandbox);
    }

    @Override
    public List<Sandbox> searchAll() {
        return this.jpaSandbox.findAll();
    }

    @Override
    public void removeAll() {
        this.jpaSandbox.deleteAll();
    }

    @Override
    public List<Sandbox> searchByName(String name) {
        return this.jpaSandbox.findByName(name);
    }
}
