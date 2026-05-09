package com.onlineservise.service;

import com.onlineservise.dto.ServiceDTO;
import java.util.List;

public interface ServiceService {
    List<ServiceDTO> getAllServices();
    ServiceDTO getServiceById(Long id);
    ServiceDTO saveService(ServiceDTO serviceDTO);
    void updateService(ServiceDTO serviceDTO);
    void deleteService(Long id);
}
