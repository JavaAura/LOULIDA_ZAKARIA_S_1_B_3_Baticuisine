package main.com.baticuisine.repository.Implementation;


import main.com.baticuisine.DatabaseConnection.DatabaseConnection;
import main.com.baticuisine.model.*;

import java.sql.*;
import java.util.*;

public class ProjectRepositoryImpl {

    private Connection connection;

    public ProjectRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public Optional<Project> findById(UUID id) {
        String sql = "SELECT " +
                "    p.id AS project_id, " +
                "    p.project_name, " +
                "    p.surface, " +
                "    p.profit_margin, " +
                "    p.total_cost, " +
                "    p.status, " +
                "    c.id AS client_id, " +
                "    c.name AS client_name, " +
                "    c.address AS client_address, " +
                "    c.phone AS client_phone, " +
                "    c.is_professional AS client_professional, " +
                "    mat.id AS component_id, " +
                "    mat.name AS component_name, " +
                "    mat.unit_cost AS material_unit_cost, " +
                "    mat.quantity AS material_quantity, " +
                "    mat.transport_cost AS material_transport_cost, " +
                "    mat.quality_coefficient AS material_quality_coefficient, " +
                "    'MATERIAL' AS component_type " +
                "FROM project p " +
                "JOIN client c ON p.client_id = c.id " +
                "LEFT JOIN material mat ON mat.project_id = p.id " +
                "WHERE p.id = ? " +
                "UNION ALL " +
                "SELECT " +
                "    p.id AS project_id, " +
                "    p.project_name, " +
                "    p.surface, " +
                "    p.profit_margin, " +
                "    p.total_cost, " +
                "    p.status, " +
                "    c.id AS client_id, " +
                "    c.name AS client_name, " +
                "    c.address AS client_address, " +
                "    c.phone AS client_phone, " +
                "    c.is_professional AS client_professional, " +
                "    lab.id AS component_id, " +
                "    lab.name AS component_name, " +
                "    NULL AS material_unit_cost, " +
                "    NULL AS material_quantity, " +
                "    NULL AS material_transport_cost, " +
                "    NULL AS material_quality_coefficient, " +
                "    lab.hourly_rate AS labor_hourly_rate, " +
                "    lab.hours_worked AS labor_hours_worked, " +
                "    lab.productivity_factor AS labor_productivity_factor, " +
                "    'LABOR' AS component_type " +
                "FROM project p " +
                "JOIN client c ON p.client_id = c.id " +
                "LEFT JOIN labor lab ON lab.project_id = p.id " +
                "WHERE p.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.setObject(2, id);
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




    public Optional<Project> findByName(String name) {
        String sql = "SELECT " +
                "    p.id , " +
                "    p.project_name, " +
                "    p.surface, " +
                "    p.profit_margin, " +
                "    p.total_cost, " +
                "    p.status, " +
                "    c.id AS client_id, " +
                "    c.name AS client_name, " +
                "    c.address AS client_address, " +
                "    c.phone AS client_phone, " +
                "    c.is_professional AS client_professional, " +
                "    mat.id AS component_id, " +
                "    mat.name AS component_name, " +
                "    mat.unit_cost AS material_unit_cost, " +
                "    mat.quantity AS material_quantity, " +
                "    mat.transport_cost AS material_transport_cost, " +
                "    mat.quality_coefficient AS material_quality_coefficient, " +
                "NULL AS labor_hourly_rate, " +
        "        NULL AS labor_hours_worked, " +
                "        NULL AS labor_productivity_factor, " +
                "    'MATERIAL' AS component_type " +
                "FROM project p " +
                "JOIN client c ON p.client_id = c.id " +
                "LEFT JOIN material mat ON mat.project_id = p.id " +
                "WHERE p.project_name = ? " +
                "UNION ALL " +
                "SELECT " +
                "    p.id ," +
                "    p.project_name, " +
                "    p.surface, " +
                "    p.profit_margin, " +
                "    p.total_cost, " +
                "    p.status, " +
                "    c.id AS client_id, " +
                "    c.name AS client_name, " +
                "    c.address AS client_address, " +
                "    c.phone AS client_phone, " +
                "    c.is_professional AS client_professional, " +
                "    lab.id AS component_id, " +
                "    lab.name AS component_name, " +
                "    NULL AS material_unit_cost, " +
                "    NULL AS material_quantity, " +
                "    NULL AS material_transport_cost, " +
                "    NULL AS material_quality_coefficient, " +
                "    lab.hourly_rate AS labor_hourly_rate, " +
                "    lab.hours_worked AS labor_hours_worked, " +
                "    lab.productivity_factor AS labor_productivity_factor, " +
                "    'LABOR' AS component_type " +
                "FROM project p " +
                "JOIN client c ON p.client_id = c.id " +
                "LEFT JOIN labor lab ON lab.project_id = p.id " +
                "WHERE p.project_name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, name);
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
        Map<UUID, Project> projectMap = new HashMap<>();

        String sql =
                "SELECT " +
                        "    p.id AS project_id, " +
                        "    p.project_name, " +
                        "    p.surface, " +
                        "    p.profit_margin, " +
                        "    p.total_cost, " +
                        "    p.status, " +
                        "    c.id AS client_id, " +
                        "    c.name AS client_name, " +
                        "    c.address AS client_address, " +
                        "    c.phone AS client_phone, " +
                        "    c.is_professional AS client_professional, " +
                        "    comp.component_id, " +
                        "    comp.component_name, " +
                        "    comp.material_unit_cost, " +
                        "    comp.material_quantity, " +
                        "    comp.material_transport_cost, " +
                        "    comp.material_quality_coefficient, " +
                        "    comp.labor_hourly_rate, " +
                        "    comp.labor_hours_worked, " +
                        "    comp.labor_productivity_factor, " +
                        "    comp.component_type " +
                        "FROM project p " +
                        "JOIN client c ON p.client_id = c.id " +
                        "LEFT JOIN ( " +
                        "    SELECT " +
                        "        mat.project_id, " +
                        "        mat.id AS component_id, " +
                        "        mat.name AS component_name, " +
                        "        mat.unit_cost AS material_unit_cost, " +
                        "        mat.quantity AS material_quantity, " +
                        "        mat.transport_cost AS material_transport_cost, " +
                        "        mat.quality_coefficient AS material_quality_coefficient, " +
                        "        NULL AS labor_hourly_rate, " +
                        "        NULL AS labor_hours_worked, " +
                        "        NULL AS labor_productivity_factor, " +
                        "        'MATERIAL' AS component_type " +
                        "    FROM material mat " +
                        "    UNION ALL " +
                        "    SELECT " +
                        "        lab.project_id, " +
                        "        lab.id AS component_id, " +
                        "        lab.name AS component_name, " +
                        "        NULL AS material_unit_cost, " +
                        "        NULL AS material_quantity, " +
                        "        NULL AS material_transport_cost, " +
                        "        NULL AS material_quality_coefficient, " +
                        "        lab.hourly_rate AS labor_hourly_rate, " +
                        "        lab.hours_worked AS labor_hours_worked, " +
                        "        lab.productivity_factor AS labor_productivity_factor, " +
                        "        'LABOR' AS component_type " +
                        "    FROM labor lab " +
                        ") comp ON comp.project_id = p.id " +
                        "ORDER BY p.id;";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                UUID projectId = UUID.fromString(rs.getString("project_id"));

                // Check if the project already exists
                Project project = projectMap.get(projectId);
                if (project == null) {
                    // Create a new project
                    project = new Project();
                    project.setId(projectId);
                    project.setProjectName(rs.getString("project_name"));
                    project.setSurface(rs.getDouble("surface"));
                    project.setProfitMargin(rs.getDouble("profit_margin"));
                    project.setTotalCost(rs.getDouble("total_cost"));
                    project.setStatus(ProjectStatus.valueOf(rs.getString("status")));

                    // Set the client
                    Client client = new Client();
                    client.setId(UUID.fromString(rs.getString("client_id")));
                    client.setName(rs.getString("client_name"));
                    client.setAddress(rs.getString("client_address"));
                    client.setPhone(rs.getString("client_phone"));
                    client.setProfessional(rs.getBoolean("client_professional"));
                    project.setClient(client);

                    // Initialize components list
                    project.setComponents(new ArrayList<>());

                    // Add the project to the map and list
                    projectMap.put(projectId, project);
                    projects.add(project);
                }

                // Process the component if it exists
                String componentIdStr = rs.getString("component_id");
                if (componentIdStr != null) {
                    UUID componentId = UUID.fromString(componentIdStr);
                    String componentType = rs.getString("component_type");
                    Component component;

                    if ("MATERIAL".equals(componentType)) {
                        Material material = new Material();
                        material.setId(componentId);
                        material.setName(rs.getString("component_name"));
                        material.setUnitCost(rs.getDouble("material_unit_cost"));
                        material.setQuantity(rs.getDouble("material_quantity"));
                        material.setTransportCost(rs.getDouble("material_transport_cost"));
                        material.setQualityCoefficient(rs.getDouble("material_quality_coefficient"));
                        component = material;
                    } else if ("LABOR".equals(componentType)) {
                        Labor labor = new Labor();
                        labor.setId(componentId);
                        labor.setName(rs.getString("component_name"));
                        labor.setHourlyRate(rs.getDouble("labor_hourly_rate"));
                        labor.setHoursWorked(rs.getDouble("labor_hours_worked"));
                        labor.setProductivityFactor(rs.getDouble("labor_productivity_factor"));
                        component = labor;
                    } else {
                        continue; // Skip if component type is unknown
                    }

                    // Add component to project's component list
                    project.getComponents().add(component);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public void save(Project project) {
        String sql = "INSERT INTO project (id, project_name, status, client_id ,surface) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, project.getId());
            stmt.setString(2, project.getProjectName());

            stmt.setObject(3, project.getStatus(), Types.OTHER); // Correctly set enum type

            stmt.setObject(4, project.getClient().getId());
            stmt.setDouble(5,project.getSurface());
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
        project.setSurface(rs.getDouble("surface"));
        project.setStatus(ProjectStatus.valueOf(rs.getString("status")));

        // Fetch and set the client
        Client client = new Client();
        client.setId(UUID.fromString(rs.getString("client_id")));
        client.setName(rs.getString("client_name"));
        client.setAddress(rs.getString("client_address"));
        client.setPhone(rs.getString("client_phone"));
        client.setProfessional(rs.getBoolean("client_professional"));
        project.setClient(client);

        // Initialize materials and labor lists
        List<Material> materials = new ArrayList<>();
        List<Labor> labors = new ArrayList<>();
        List<Component> components =new ArrayList<>();

        // Process materials and labor
        do {
            String componentType = rs.getString("component_type");
            if ("MATERIAL".equals(componentType)) {
                Material material = new Material();
                material.setId(UUID.fromString(rs.getString("component_id")));
                material.setName(rs.getString("component_name"));
                material.setUnitCost(rs.getDouble("material_unit_cost"));
                material.setQuantity(rs.getDouble("material_quantity"));
                material.setTransportCost(rs.getDouble("material_transport_cost"));
                material.setQualityCoefficient(rs.getDouble("material_quality_coefficient"));
                materials.add(material);
            } else if ("LABOR".equals(componentType)) {
                Labor labor = new Labor();
                labor.setId(UUID.fromString(rs.getString("component_id")));
                labor.setName(rs.getString("component_name"));
                labor.setHourlyRate(rs.getDouble("labor_hourly_rate"));
                labor.setHoursWorked(rs.getDouble("labor_hours_worked"));
                labor.setProductivityFactor(rs.getDouble("labor_productivity_factor"));
                labors.add(labor);
            }
        } while (rs.next());
components.addAll(materials);
       components.addAll(labors);
        project.setComponents(components);

        return project;
    }

}
