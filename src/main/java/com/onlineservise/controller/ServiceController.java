package com.onlineservise.controller;

import com.onlineservise.dto.ServiceDTO;
import com.onlineservise.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping
    public List<ServiceDTO> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ServiceDTO getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }

    @PostMapping
    public ServiceDTO createService(@RequestBody ServiceDTO serviceDTO) {
        return serviceService.saveService(serviceDTO);
    }

    @PutMapping("/{id}")
    public void updateService(@PathVariable Long id, @RequestBody ServiceDTO serviceDTO) {
        serviceDTO.setId(id);
        serviceService.updateService(serviceDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }
}
