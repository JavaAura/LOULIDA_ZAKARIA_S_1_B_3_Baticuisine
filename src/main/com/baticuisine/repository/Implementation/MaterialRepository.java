package main.com.baticuisine.repository.Implementation;

import main.com.baticuisine.DatabaseConnection.DatabaseConnection;
import main.com.baticuisine.model.Component;
import main.com.baticuisine.model.Material;
import main.com.baticuisine.model.componentType;
import main.com.baticuisine.repository.Interfaces.ComponentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialRepository implements ComponentRepository {

    private Connection connection;

    public MaterialRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }




    public Optional<List<Component>> findByProjectId(UUID projectId) {
        List<Component> components = new ArrayList<>();
        String sql = "SELECT * FROM material WHERE project_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Component component = mapResultSetToMaterial(rs);
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return components.isEmpty() ? Optional.empty() : Optional.of(components);
    }


    @Override
    public void save(Component component) {
        if (!(component instanceof Material)) {
            throw new IllegalArgumentException("Component must be of type Material");
        }
        Material material = (Material) component;
        String sql = "INSERT INTO material (id, name, unit_cost, quantity, transport_cost, quality_coefficient, project_id,component_type,vat_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, material.getId());
            stmt.setString(2, material.getName());

            stmt.setDouble(3, material.getUnitCost());
            stmt.setDouble(4, material.getQuantity());
            stmt.setDouble(5, material.getTransportCost());
            stmt.setDouble(6, material.getQualityCoefficient());
            stmt.setObject(7, material.getProject_id());
            stmt.setObject(8, material.getComponentType(), Types.OTHER); // Correctly set enum type
            stmt.setDouble(9,material.getVatRate()); // Correctly set enum type

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Material mapResultSetToMaterial(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        String name = rs.getString("name");
        double vatRate = rs.getDouble("vat_rate");
        componentType componenttype=componentType.valueOf(rs.getString("component_type")) ;
        double unitCost = rs.getDouble("unit_cost");
        double quantity = rs.getDouble("quantity");
        double transportCost = rs.getDouble("transport_cost");
        double qualityCoefficient = rs.getDouble("quality_coefficient");
        UUID projectId = UUID.fromString(rs.getString("project_id"));
        return new Material(id, name, unitCost, quantity, transportCost, vatRate, qualityCoefficient, componenttype,projectId);
    }
}
