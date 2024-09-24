package main.com.baticuisine.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Estimate {
    private UUID id;
    private double estimatedAmount;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private boolean isAccepted;
    private UUID project_id;
    public Estimate(UUID id, double estimatedAmount, LocalDate issueDate, LocalDate validityDate, boolean isAccepted,UUID project_id) {
        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.isAccepted = isAccepted;
        this.project_id =project_id;
    }

    public Estimate() {
        this.id = UUID.randomUUID();
        isAccepted =false;
        // Automatically generate a new UUID when creating a project
    }

    public UUID getProject_id() {
        return project_id;
    }

    public void setProject_id(UUID project_id) {
        this.project_id = project_id;
    }

    public void acceptEstimate() {
        this.isAccepted = true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    @Override
    public String toString() {
        return "Estimate{" +
                "id=" + id +
                ", estimatedAmount=" + estimatedAmount +
                ", issueDate=" + issueDate +
                ", validityDate=" + validityDate +
                ", isAccepted=" + isAccepted +
                ", project_id=" + project_id +
                '}';
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
