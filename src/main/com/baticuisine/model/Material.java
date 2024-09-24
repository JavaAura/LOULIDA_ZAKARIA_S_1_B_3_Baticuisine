package main.com.baticuisine.model;

import java.util.UUID;

public class Material extends Component {
    private double unitCost;
    private double quantity;
    private double transportCost;
    private double qualityCoefficient;

    public Material(UUID id , String name, double unitCost, double quantity, double transportCost, double vatRate, double qualityCoefficient,componentType componentType, UUID project_id) {
        super(id,name, vatRate, componentType,project_id);
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    public Material() {

    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    public double getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    public double calculateCostWithoutVAT() {
        return (unitCost * quantity * qualityCoefficient) + transportCost;
    }

    // Method to calculate total cost with VAT
    public double calculateCostWithVAT() {
        return calculateCostWithoutVAT() * (1 +  vatRate );
    }


}

