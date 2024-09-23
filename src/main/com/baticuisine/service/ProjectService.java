package main.com.baticuisine.service;


import main.com.baticuisine.model.Project;
import main.com.baticuisine.repository.Implementation.ProjectRepositoryImpl;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectService {

    private final ProjectRepositoryImpl projectRepository;

    public ProjectService(ProjectRepositoryImpl projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void createProject(Project project) {
        projectRepository.save(project);
    }

    public Optional<Project> getProjectById(UUID id) {
        return projectRepository.findById(id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void updateProject(Project project) {
        projectRepository.update(project);
    }

    public void deleteProject(UUID id) {
        projectRepository.delete(id);
    }
}
