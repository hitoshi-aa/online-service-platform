package com.onlineservise.service;

import com.onlineservise.dto.ClientDTO;
import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClients();
    List<ClientDTO> searchClientsByName(String name);
    ClientDTO getClientById(Long id);
    ClientDTO saveClient(ClientDTO clientDTO);
    void updateClient(ClientDTO clientDTO);
    void deleteClient(Long id);
}
