package main.com.baticuisine.controller;


import main.com.baticuisine.model.Component;
import main.com.baticuisine.model.Project;
import main.com.baticuisine.service.ComponentService;
import main.com.baticuisine.service.ProjectService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectController {

    private final ProjectService projectService;
    private final ComponentService componentService;
    public ProjectController() {
        this.projectService = new ProjectService();
        this.componentService=new ComponentService();
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
    public Project getProjectByName(String name) {
        Optional<Project>  project = projectService.getProjectByName(name);

        if (project.isPresent()) {

            Project projecttostring = project.get();  // Unwrap the Optional
          return projecttostring;  // This will call toString() method of Professeur or Etudiant
        } else {
            System.out.println("Project not found.");
            return null;
        }
    }

    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }


  public double calculateTotalCost(Project project,double vatRate,double marginRate){
      Optional<List <Component>> components = componentService.getComponentByProjectId(project.getId());

       components.ifPresent(project::setComponents);
       double totalcost = project.getComponents().stream().mapToDouble(Component::calculateCostWithVAT).sum();
       totalcost += totalcost *vatRate;
       totalcost += totalcost * marginRate ;
       return totalcost ;
  }


//    public double calculateTotalCost(Project project, double vatRate, double marginRate) {
//        Optional<List<Component>> optionalComponents = componentService.getComponentByProjectId(project.getId());
//
//        // If components are present, set them in the project
//        optionalComponents.ifPresent(project::setComponents);
//
//        // You can now continue to work with the components inside the project for cost calculations
//        // Example: Calculate total cost based on components
//        double totalCost = project.getComponents().stream()
//                .mapToDouble(Component::getCost) // Assuming each component has a getCost() method
//                .sum();
//
//        // Apply VAT and margin rates to total cost
//        totalCost += totalCost * vatRate;
//        totalCost += totalCost * marginRate;
//
//        return totalCost;
//    }


}




