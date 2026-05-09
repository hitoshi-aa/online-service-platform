package com.onlineservise.service;

import com.onlineservise.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();

    Client saveClient(Client client);
}
