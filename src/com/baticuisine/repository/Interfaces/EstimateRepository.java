package com.baticuisine.repository.Interfaces;


import com.baticuisine.model.Estimate;

import java.util.List;

public interface EstimateRepository {
    void save(Estimate estimate);
    Estimate findById(String id);
    List<Estimate> findAll();
    void update(Estimate estimate);
    void delete(String id);
}
