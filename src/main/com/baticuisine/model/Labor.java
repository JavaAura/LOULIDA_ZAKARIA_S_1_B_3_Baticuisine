package main.com.baticuisine.model;

import java.util.UUID;

public class Labor extends Component {
    private double hourlyRate;
    private double hoursWorked;
    private double productivityFactor;

    public Labor(UUID id , String name, double hourlyRate, double hoursWorked, double vatRate, double productivityFactor,componentType componentType, UUID project_id) {
        super(id ,name, vatRate,componentType,project_id);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.productivityFactor = productivityFactor;
    }

    public Labor() {
    }

    // Method to calculate labor cost without VAT
    public double calculateCostWithoutVAT() {
        return hourlyRate * hoursWorked * productivityFactor;
    }

    // Method to calculate labor cost with VAT
    public double calculateCostWithVAT() {
        return calculateCostWithoutVAT() * (1 + vatRate );
    }
    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getProductivityFactor() {
        return productivityFactor;
    }

    public void setProductivityFactor(double productivityFactor) {
        this.productivityFactor = productivityFactor;
    }


}
