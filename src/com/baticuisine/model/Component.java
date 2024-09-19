package com.baticuisine.model;

import java.util.UUID;

public abstract class Component {
    private UUID id;
    protected String name;
    protected double vatRate;
    protected String componentType;
    private UUID project_id;

    public Component(UUID id ,String name, double vatRate, String componentType ,UUID project_id) {
        this.id = id;
        this.name = name;
        this.vatRate = vatRate;
        this.componentType = componentType;
        this.project_id=project_id;
    }

    public Component() {
        this.id = UUID.randomUUID();
    }

    public UUID getProject_id() {
        return project_id;
    }

    public void setProject_id(UUID project_id) {
        this.project_id = project_id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public abstract double calculateCost();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }
}
