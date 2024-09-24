package main.com.baticuisine.repository.Interfaces;


import main.com.baticuisine.model.Estimate;

import java.util.List;
import java.util.UUID;

public interface EstimateRepository {
    void save(Estimate estimate);
    Estimate findById(UUID id);
    List<Estimate> findAll();
    void update(Estimate estimate);
    void delete(String id);
}
