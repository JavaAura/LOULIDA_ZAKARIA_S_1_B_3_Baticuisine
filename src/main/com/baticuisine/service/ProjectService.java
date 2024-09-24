package main.com.baticuisine.service;


import main.com.baticuisine.model.Component;
import main.com.baticuisine.model.Project;
import main.com.baticuisine.repository.Implementation.LaborRepository;
import main.com.baticuisine.repository.Implementation.MaterialRepository;
import main.com.baticuisine.repository.Implementation.ProjectRepositoryImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectService {

    private final ProjectRepositoryImpl projectRepository;
    private final LaborRepository laborRepository;
    private final MaterialRepository materialRepository;


    public ProjectService() {
        this.projectRepository = new ProjectRepositoryImpl();
        this.laborRepository =new LaborRepository();
        this.materialRepository =new MaterialRepository();
    }

    public void createProject(Project project) {
        projectRepository.save(project);
    }

    public Optional<Project> getProjectById(UUID id) {
        return projectRepository.findById(id);
    }
    public Optional<Project> getProjectByName(String name) {
       Optional <Project> project = projectRepository.findByName(name);
       if (project.isPresent()) {
           return  project;
       }else{
        return  null;
    }}

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

}
