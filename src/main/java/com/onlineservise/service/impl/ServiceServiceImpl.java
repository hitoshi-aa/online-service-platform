package com.onlineservise.service.impl;

import com.onlineservise.entity.Service;
import com.onlineservise.repository.ServiceRepository;
import com.onlineservise.service.ServiceService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public List<Service> getAllServices() {

        return serviceRepository.findAll();
    }
}
