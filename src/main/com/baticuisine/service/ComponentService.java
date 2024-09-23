package main.com.baticuisine.service;


import main.com.baticuisine.model.Component;
import main.com.baticuisine.repository.Interfaces.ComponentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ComponentService {

    private final ComponentRepository componentRepository;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public void createComponent(Component component) {
        componentRepository.save(component);
    }

    public Optional<Component> getComponentById(UUID id) {
        return componentRepository.findById(id);
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    public void updateComponent(Component component) {
        componentRepository.update(component);
    }

    public void deleteComponent(UUID id) {
        componentRepository.delete(id);
    }
}

