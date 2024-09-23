package main.com.baticuisine.service;


import main.com.baticuisine.model.Client;
import main.com.baticuisine.repository.Implementation.ClientRepositoryImpl;
import main.com.baticuisine.repository.Interfaces.ClientRepository;

import java.util.List;
import java.util.UUID;

public class ClientService {

    private final ClientRepositoryImpl clientRepository;

    public ClientService() {
        this.clientRepository = new ClientRepositoryImpl();
    }

    public void createClient(Client client) {
        clientRepository.save(client);
    }

    public Client getClientById(UUID id) {
        return clientRepository.findById(id.toString());
    }

    public Client getClientByName(String name) {
        return clientRepository.findByName(name);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void updateClient(Client client) {
        clientRepository.update(client);
    }

    public void deleteClient(UUID id) {
        clientRepository.delete(id.toString());
    }
}
