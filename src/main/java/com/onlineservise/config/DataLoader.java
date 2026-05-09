package com.onlineservise.config;

import com.onlineservise.entity.Client;
import com.onlineservise.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClientRepository clientRepository;

    @Override
    public void run(String... args) {

        System.out.println();
        System.out.println("===== CLIENTS =====");

        for (Client client : clientRepository.findAll()) {

            System.out.println(
                    "ID: " + client.getId()
                            + " | NAME: " + client.getName()
                            + " | PHONE: " + client.getPhone()
            );
        }

        System.out.println("===================");
        System.out.println();
    }
}
