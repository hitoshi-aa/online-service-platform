package com.onlineservise.repository;

import com.onlineservise.entity.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();
    Optional<Client> findById(Long id);
    List<Client> findByNameContaining(String name);
    Client save(Client client);
    void update(Client client);
    void deleteById(Long id);
}
