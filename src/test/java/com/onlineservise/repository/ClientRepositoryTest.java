package com.onlineservise.repository;

import com.onlineservise.entity.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void shouldReturnAllClients() {

        List<Client> clients = clientRepository.findAll();

        Assertions.assertFalse(clients.isEmpty());

        System.out.println("===== CLIENT TEST =====");

        for (Client client : clients) {
            System.out.println(client.getName());
        }
    }
}
