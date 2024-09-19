package com.baticuisine.repository.Interfaces;


import com.baticuisine.model.Component;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

public interface ComponentRepository {
    Optional<Component> findById(UUID id);
    List<Component> findAll();
    void save(Component component);
    void update(Component component);
    void delete(UUID id);
}
