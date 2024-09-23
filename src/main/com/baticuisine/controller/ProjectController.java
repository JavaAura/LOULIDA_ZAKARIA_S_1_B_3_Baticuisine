package main.com.baticuisine.controller;


import main.com.baticuisine.model.Project;
import main.com.baticuisine.service.ProjectService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void createProject(Project project) {
        projectService.createProject(project);
    }

    public void getProjectById(UUID id) {
        Optional<Project>  project = projectService.getProjectById(id);

        if (project.isPresent()) {
            Project projecttostring = project.get();  // Unwrap the Optional
            System.out.println(projecttostring);  // This will call toString() method of Professeur or Etudiant
        } else {
            System.out.println("Project not found.");
        }

    }

    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    public void updateProject(Project project) {
        projectService.updateProject(project);
    }

    public void deleteProject(UUID id) {
        projectService.deleteProject(id);
    }
}
