package main.com.baticuisine.controller;


import main.com.baticuisine.model.Client;
import main.com.baticuisine.service.ClientService;

import java.util.List;
import java.util.UUID;

public class ClientController {

    private final ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    public void createClient(Client client) {
        clientService.createClient(client);
    }

    public Client getClientById(UUID id) {
        return clientService.getClientById(id);
    }
    public Client getClientByName(String name) {
        return clientService.getClientByName(name);
    }

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    public void updateClient(Client client) {
        clientService.updateClient(client);
    }

    public void deleteClient(UUID id) {
        clientService.deleteClient(id);
    }
}
