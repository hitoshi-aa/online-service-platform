package com.onlineservise.controller;

import com.onlineservise.entity.Client;
import com.onlineservise.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAllClients() {

        return clientRepository.findAll();
    }
}
