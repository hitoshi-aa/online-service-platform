package com.onlineservise.service.impl;

import com.onlineservise.entity.Client;
import com.onlineservise.repository.ClientRepository;
import com.onlineservise.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {

        log.info("Getting all clients");

        return clientRepository.findAll();
    }

    @Override
    public Client saveClient(Client client) {

        if (client.getName() == null || client.getName().isBlank()) {
            throw new RuntimeException("Client name cannot be empty");
        }

        if (client.getPhone() == null || client.getPhone().isBlank()) {
            throw new RuntimeException("Phone cannot be empty");
        }

        log.info("Saving client: {}", client.getName());

        return clientRepository.save(client);
    }
}
