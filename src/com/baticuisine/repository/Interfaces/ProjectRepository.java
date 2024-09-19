package com.baticuisine.repository.Interfaces;

import com.baticuisine.model.Project;

import java.util.List;

public interface ProjectRepository {
    void save(Project project);
    Project findById(int id);
    List<Project> findAll();
    void update(Project project);
    void delete(int id);
}
