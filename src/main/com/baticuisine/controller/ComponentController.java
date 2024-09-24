package main.com.baticuisine.controller;



import main.com.baticuisine.model.Component;
import main.com.baticuisine.model.Project;
import main.com.baticuisine.service.ComponentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ComponentController {

    private final ComponentService componentService;

    public ComponentController() {

        this.componentService = new ComponentService();
    }

    public void createComponents(List<Component> components) {
        components.stream().forEach(component ->componentService.createComponent(component));

    }

//    public void getComponentById(UUID id) {
//        Optional<Component> component= componentService.getComponentById(id);
//
//        if (component.isPresent()) {
//            Component componenttostring = component.get();  // Unwrap the Optional
//            System.out.println(componenttostring);  // This will call toString() method of Professeur or Etudiant
//        } else {
//            System.out.println("Project not found.");
//        }
//    }
//
//    public List<Component> getAllComponents() {
//        return componentService.getAllComponents();
//    }
//
//    public void updateComponent(Component component) {
//        componentService.updateComponent(component);
//    }
//
//    public void deleteComponent(UUID id) {
//        componentService.deleteComponent(id);
//    }
}
