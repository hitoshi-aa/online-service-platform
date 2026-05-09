package com.onlineservise.service.impl;

import com.onlineservise.dto.ClientDTO;
import com.onlineservise.entity.Client;
import com.onlineservise.repository.ClientRepository;
import com.onlineservise.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDTO> searchClientsByName(String name) {
        return clientRepository.findByNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return clientRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        validateClient(clientDTO);
        Client client = convertToEntity(clientDTO);
        return convertToDTO(clientRepository.save(client));
    }

    @Override
    public void updateClient(ClientDTO clientDTO) {
        validateClient(clientDTO);
        clientRepository.update(convertToEntity(clientDTO));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    private void validateClient(ClientDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new RuntimeException("Client name cannot be empty");
        }
        if (dto.getPhone() == null || dto.getPhone().isBlank()) {
            throw new RuntimeException("Phone cannot be empty");
        }
    }

    private ClientDTO convertToDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .phone(client.getPhone())
                .build();
    }

    private Client convertToEntity(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setPhone(dto.getPhone());
        return client;
    }
}
