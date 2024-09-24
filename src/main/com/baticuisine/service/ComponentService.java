package main.com.baticuisine.service;


import main.com.baticuisine.model.Component;
import main.com.baticuisine.model.componentType;
import main.com.baticuisine.repository.Implementation.LaborRepository;
import main.com.baticuisine.repository.Implementation.MaterialRepository;
import main.com.baticuisine.repository.Interfaces.ComponentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ComponentService {

    private final MaterialRepository MaterialRepository;
    private final LaborRepository LaborRepository;
    public ComponentService() {

        this.MaterialRepository=new MaterialRepository();
        this.LaborRepository =new LaborRepository();
    }

    public void createComponent(Component component) {
        if (component.getComponentType() == componentType.Materialtype) {
            MaterialRepository.save(component);
        } else if (component.getComponentType() == componentType.Labortype) {
           LaborRepository.save(component);
        }
    }


    public Optional<List<Component>> getComponentByProjectId(UUID id) {
        Optional<List<Component>> list = MaterialRepository.findByProjectId(id);
        if (list.isPresent()){
          return list;
        }else{
            return null;
        }
    }





}

