package main.com.baticuisine.service;


import main.com.baticuisine.model.Estimate;
import main.com.baticuisine.repository.Implementation.EstimateRepositoryImpl;
import main.com.baticuisine.repository.Interfaces.EstimateRepository;

import java.util.List;
import java.util.UUID;

public class EstimateService {

    private final EstimateRepositoryImpl estimateRepository;

    public EstimateService() {
        this.estimateRepository = new EstimateRepositoryImpl();
    }

    public void createEstimate(Estimate estimate) {
        estimateRepository.save(estimate);
    }

    public Estimate getEstimateById(UUID id) {
        return estimateRepository.findById(id);
    }





    public void deleteEstimate(UUID id) {
        estimateRepository.delete(id.toString());
    }
}
