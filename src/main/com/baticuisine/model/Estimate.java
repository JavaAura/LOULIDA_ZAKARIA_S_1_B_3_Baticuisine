package main.com.baticuisine.model;

import java.util.Date;
import java.util.UUID;

public class Estimate {
    private UUID id;
    private double estimatedAmount;
    private Date issueDate;
    private Date validityDate;
    private boolean isAccepted;

    public Estimate(UUID id, double estimatedAmount, Date issueDate, Date validityDate, boolean isAccepted) {
        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.isAccepted = isAccepted;
    }

    public Estimate(UUID id) {
        this.id = UUID.randomUUID(); // Automatically generate a new UUID when creating a project
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
