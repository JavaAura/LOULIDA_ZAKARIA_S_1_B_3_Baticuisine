package main.com.baticuisine.controller;


import main.com.baticuisine.model.Estimate;
import main.com.baticuisine.service.EstimateService;

import java.util.List;
import java.util.UUID;

public class EstimateController {

    private final EstimateService estimateService;

    public EstimateController() {
        this.estimateService = new EstimateService();
    }

    public void createEstimate(Estimate estimate) {
        estimateService.createEstimate(estimate);
    }

    public Estimate getEstimateById(UUID id) {
        return estimateService.getEstimateById(id);
    }


    public void deleteEstimate(UUID id) {
        estimateService.deleteEstimate(id);
    }
}

