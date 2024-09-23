package main.com.baticuisine.repository.Implementation;


import main.com.baticuisine.DatabaseConnection.DatabaseConnection;
import main.com.baticuisine.model.Estimate;
import main.com.baticuisine.repository.Interfaces.EstimateRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EstimateRepositoryImpl implements EstimateRepository {

    private Connection connection;

    public EstimateRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void save(Estimate estimate) {
        String sql = "INSERT INTO estimate (id, estimated_amount, issue_date, validity_date, is_accepted) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, estimate.getId());
            stmt.setDouble(2, estimate.getEstimatedAmount());
            stmt.setDate(3, new java.sql.Date(estimate.getIssueDate().getTime()));
            stmt.setDate(4, new java.sql.Date(estimate.getValidityDate().getTime()));
            stmt.setBoolean(5, estimate.isAccepted());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Estimate findById(String id) {
        String sql = "SELECT * FROM estimate WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, UUID.fromString(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEstimate(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Estimate> findAll() {
        List<Estimate> estimates = new ArrayList<>();
        String sql = "SELECT * FROM estimate";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                estimates.add(mapResultSetToEstimate(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estimates;
    }

    @Override
    public void update(Estimate estimate) {
        String sql = "UPDATE estimate SET estimated_amount = ?, issue_date = ?, validity_date = ?, is_accepted = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, estimate.getEstimatedAmount());
            stmt.setDate(2, new java.sql.Date(estimate.getIssueDate().getTime()));
            stmt.setDate(3, new java.sql.Date(estimate.getValidityDate().getTime()));
            stmt.setBoolean(4, estimate.isAccepted());
            stmt.setObject(5, estimate.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM estimate WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, UUID.fromString(id));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Estimate mapResultSetToEstimate(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        double estimatedAmount = rs.getDouble("estimated_amount");
        Date issueDate = rs.getDate("issue_date");
        Date validityDate = rs.getDate("validity_date");
        boolean isAccepted = rs.getBoolean("is_accepted");
        return new Estimate(id, estimatedAmount, issueDate, validityDate, isAccepted);
    }
}
