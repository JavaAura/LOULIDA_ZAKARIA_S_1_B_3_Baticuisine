package main.com.baticuisine.repository.Implementation;


import main.com.baticuisine.model.Project;
import main.com.baticuisine.model.ProjectStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectRepositoryImpl {

    private Connection connection;

    public ProjectRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    public Optional<Project> findById(UUID id) {
        String sql = "SELECT * FROM project WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Project project = mapResultSetToProject(rs);
                return Optional.of(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM project";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Project project = mapResultSetToProject(rs);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public void save(Project project) {
        String sql = "INSERT INTO project (id, project_name, profit_margin, total_cost, status, client_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, project.getId());
            stmt.setString(2, project.getProjectName());
            stmt.setDouble(3, project.getProfitMargin());
            stmt.setDouble(4, project.getTotalCost());
            stmt.setString(5, project.getStatus().name());
            stmt.setObject(6, project.getClient().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Project project) {
        String sql = "UPDATE project SET project_name = ?, profit_margin = ?, total_cost = ?, status = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, project.getProjectName());
            stmt.setDouble(2, project.getProfitMargin());
            stmt.setDouble(3, project.getTotalCost());
            stmt.setString(4, project.getStatus().name());
            stmt.setObject(5, project.getClient().getId());
            stmt.setObject(6, project.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM project WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Project mapResultSetToProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(UUID.fromString(rs.getString("id")));
        project.setProjectName(rs.getString("project_name"));
        project.setProfitMargin(rs.getDouble("profit_margin"));
        project.setTotalCost(rs.getDouble("total_cost"));
        project.setStatus(ProjectStatus.valueOf(rs.getString("status")));
        // Fetch client and components separately
        return project;
    }
}
