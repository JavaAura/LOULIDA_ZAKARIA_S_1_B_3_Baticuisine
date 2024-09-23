package main.com.baticuisine.repository.Implementation;



import main.com.baticuisine.model.Component;
import main.com.baticuisine.model.Labor;
import main.com.baticuisine.repository.Interfaces.ComponentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LaborRepository implements ComponentRepository {

    private Connection connection;

    public LaborRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Component> findById(UUID id) {
        String sql = "SELECT * FROM labor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToLabor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Component> findAll() {
        List<Component> laborList = new ArrayList<>();
        String sql = "SELECT * FROM labor";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                laborList.add(mapResultSetToLabor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laborList;
    }

    @Override
    public void save(Component component) {
        if (!(component instanceof Labor)) {
            throw new IllegalArgumentException("Component must be of type Labor");
        }
        Labor labor = (Labor) component;
        String sql = "INSERT INTO labor (id, name, vat_rate, hourly_rate, hours_worked, productivity_factor, project_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, labor.getId());
            stmt.setString(2, labor.getName());
            stmt.setDouble(3, labor.getVatRate());
            stmt.setDouble(4, labor.getHourlyRate());
            stmt.setDouble(5, labor.getHoursWorked());
            stmt.setDouble(6, labor.getProductivityFactor());
            stmt.setObject(7, labor.getProject_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Component component) {
        if (!(component instanceof Labor)) {
            throw new IllegalArgumentException("Component must be of type Labor");
        }
        Labor labor = (Labor) component;
        String sql = "UPDATE labor SET name = ?, vat_rate = ?, hourly_rate = ?, hours_worked = ?, productivity_factor = ?, project_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, labor.getName());
            stmt.setDouble(2, labor.getVatRate());
            stmt.setDouble(3, labor.getHourlyRate());
            stmt.setDouble(4, labor.getHoursWorked());
            stmt.setDouble(5, labor.getProductivityFactor());
            stmt.setObject(6, labor.getProject_id());
            stmt.setObject(7, labor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM labor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Labor mapResultSetToLabor(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        String name = rs.getString("name");
        double vatRate = rs.getDouble("vat_rate");
        double hourlyRate = rs.getDouble("hourly_rate");
        double hoursWorked = rs.getDouble("hours_worked");
        double productivityFactor = rs.getDouble("productivity_factor");
        UUID projectId = UUID.fromString(rs.getString("project_id"));
        return new Labor(id, name, hourlyRate, hoursWorked, vatRate, productivityFactor, projectId);
    }
}

