package main.com.baticuisine.service;


import main.com.baticuisine.model.Estimate;
import main.com.baticuisine.repository.Interfaces.EstimateRepository;

import java.util.List;
import java.util.UUID;

public class EstimateService {

    private final EstimateRepository estimateRepository;

    public EstimateService(EstimateRepository estimateRepository) {
        this.estimateRepository = estimateRepository;
    }

    public void createEstimate(Estimate estimate) {
        estimateRepository.save(estimate);
    }

    public Estimate getEstimateById(UUID id) {
        return estimateRepository.findById(id.toString());
    }

    public List<Estimate> getAllEstimates() {
        return estimateRepository.findAll();
    }

    public void updateEstimate(Estimate estimate) {
        estimateRepository.update(estimate);
    }

    public void deleteEstimate(UUID id) {
        estimateRepository.delete(id.toString());
    }
}
