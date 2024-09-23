package main.com.baticuisine.model;

import java.util.List;
import java.util.UUID;

public class Project {
    private UUID id;
    private String projectName;
    private double profitMargin;
    private double totalCost;
    private ProjectStatus status;
    private double surface;
    private Client client;
    private List<Component> components;

    public Project(UUID id,double surface ,   Estimate estimate, List<Component> components, Client client, ProjectStatus status, double totalCost, double profitMargin, String projectName) {
        this.id = id;
        this.surface =surface;
        this.estimate = estimate;
        this.components = components;
        this.client = client;
        this.status = status;
        this.projectName = projectName;
    }

    private Estimate estimate;

    public Project() {
        this.id = UUID.randomUUID(); // Automatically generate a new UUID when creating a project
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", profitMargin=" + profitMargin +
                ", totalCost=" + totalCost +
                ", status=" + status +
                ", client=" + client +
                ", components=" + components +
                ", estimate=" + estimate +
                '}';
    }

    public Estimate getEstimate() {
        return estimate;
    }

    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
    }

    public void calculateTotalCost() {
        double componentCost = components.stream()
                .mapToDouble(Component::calculateCost)
                .sum();
        this.totalCost = componentCost * (1 + profitMargin);
    }

}
