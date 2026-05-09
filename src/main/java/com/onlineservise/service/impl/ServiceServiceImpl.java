package com.onlineservise.service.impl;

import com.onlineservise.dto.ServiceDTO;
import com.onlineservise.entity.Service;
import com.onlineservise.repository.ServiceRepository;
import com.onlineservise.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDTO getServiceById(Long id) {
        return serviceRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

    @Override
    public ServiceDTO saveService(ServiceDTO dto) {
        Service service = convertToEntity(dto);
        return convertToDTO(serviceRepository.save(service));
    }

    @Override
    public void updateService(ServiceDTO dto) {
        serviceRepository.update(convertToEntity(dto));
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    private ServiceDTO convertToDTO(Service service) {
        return ServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .price(service.getPrice())
                .build();
    }

    private Service convertToEntity(ServiceDTO dto) {
        Service service = new Service();
        service.setId(dto.getId());
        service.setName(dto.getName());
        service.setPrice(dto.getPrice());
        return service;
    }
}
