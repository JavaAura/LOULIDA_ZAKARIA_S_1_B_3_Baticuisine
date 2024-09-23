package main.com.baticuisine.repository.Implementation;

import main.com.baticuisine.model.Component;
import main.com.baticuisine.model.Material;
import main.com.baticuisine.repository.Interfaces.ComponentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialRepository implements ComponentRepository {

    private Connection connection;

    public MaterialRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Component> findById(UUID id) {
        String sql = "SELECT * FROM material WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToMaterial(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Component> findAll() {
        List<Component> materialList = new ArrayList<>();
        String sql = "SELECT * FROM material";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                materialList.add(mapResultSetToMaterial(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materialList;
    }

    @Override
    public void save(Component component) {
        if (!(component instanceof Material)) {
            throw new IllegalArgumentException("Component must be of type Material");
        }
        Material material = (Material) component;
        String sql = "INSERT INTO material (id, name, vat_rate, unit_cost, quantity, transport_cost, quality_coefficient, project_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, material.getId());
            stmt.setString(2, material.getName());
            stmt.setDouble(3, material.getVatRate());
            stmt.setDouble(4, material.getUnitCost());
            stmt.setDouble(5, material.getQuantity());
            stmt.setDouble(6, material.getTransportCost());
            stmt.setDouble(7, material.getQualityCoefficient());
            stmt.setObject(8, material.getProject_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Component component) {
        if (!(component instanceof Material)) {
            throw new IllegalArgumentException("Component must be of type Material");
        }
        Material material = (Material) component;
        String sql = "UPDATE material SET name = ?, vat_rate = ?, unit_cost = ?, quantity = ?, transport_cost = ?, quality_coefficient = ?, project_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, material.getName());
            stmt.setDouble(2, material.getVatRate());
            stmt.setDouble(3, material.getUnitCost());
            stmt.setDouble(4, material.getQuantity());
            stmt.setDouble(5, material.getTransportCost());
            stmt.setDouble(6, material.getQualityCoefficient());
            stmt.setObject(7, material.getProject_id());
            stmt.setObject(8, material.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM material WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Material mapResultSetToMaterial(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        String name = rs.getString("name");
        double vatRate = rs.getDouble("vat_rate");
        double unitCost = rs.getDouble("unit_cost");
        double quantity = rs.getDouble("quantity");
        double transportCost = rs.getDouble("transport_cost");
        double qualityCoefficient = rs.getDouble("quality_coefficient");
        UUID projectId = UUID.fromString(rs.getString("project_id"));
        return new Material(id, name, unitCost, quantity, transportCost, vatRate, qualityCoefficient, projectId);
    }
}
