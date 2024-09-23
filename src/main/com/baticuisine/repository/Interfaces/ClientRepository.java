package main.com.baticuisine.repository.Interfaces;

import main.com.baticuisine.model.Client;

import java.util.List;

public interface ClientRepository {
    void save(Client client);
    Client findById(String id);
    List<Client> findAll();
    void update(Client client);
    void delete(String id);
}

