package main.com.baticuisine.repository.Implementation;



import main.com.baticuisine.DatabaseConnection.DatabaseConnection;
import main.com.baticuisine.model.Component;
import main.com.baticuisine.model.Labor;
import main.com.baticuisine.model.componentType;
import main.com.baticuisine.repository.Interfaces.ComponentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LaborRepository implements ComponentRepository {

    private Connection connection;

    public LaborRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }





    @Override
    public void save(Component component) {
        if (!(component instanceof Labor)) {
            throw new IllegalArgumentException("Component must be of type Labor");
        }
        Labor labor = (Labor) component;
        String sql = "INSERT INTO labor (id, name,  component_type,hourly_rate, hours_worked, productivity_factor, project_id,vat_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, labor.getId());
            stmt.setString(2, labor.getName());
            stmt.setObject(3, labor.getComponentType(), Types.OTHER);
            stmt.setDouble(4, labor.getHourlyRate());
            stmt.setDouble(5, labor.getHoursWorked());
            stmt.setDouble(6, labor.getProductivityFactor());
            stmt.setObject(7, labor.getProject_id());
            stmt.setDouble(8, labor.getVatRate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public Optional<List<Component>> findByProjectId(UUID projectId) {
        List<Component> components = new ArrayList<>();
        String sql = "SELECT * FROM labor WHERE project_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Component component = mapResultSetToLabor(rs);
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return components.isEmpty() ? Optional.empty() : Optional.of(components);
    }


    private Labor mapResultSetToLabor(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        String name = rs.getString("name");
        double vatRate = rs.getDouble("vat_rate");
        componentType componenttype=componentType.valueOf(rs.getString("component_type")) ;

        double hourlyRate = rs.getDouble("hourly_rate");
        double hoursWorked = rs.getDouble("hours_worked");
        double productivityFactor = rs.getDouble("productivity_factor");
        UUID projectId = UUID.fromString(rs.getString("project_id"));
        return new Labor(id, name, hourlyRate, hoursWorked, vatRate, productivityFactor,componenttype, projectId);
    }
}

