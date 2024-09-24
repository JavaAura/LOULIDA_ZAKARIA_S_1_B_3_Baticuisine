package main.com.baticuisine.repository.Interfaces;


import main.com.baticuisine.model.Component;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

public interface ComponentRepository {

    void save(Component component);

}
