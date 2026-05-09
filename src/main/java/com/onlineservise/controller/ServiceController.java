package com.onlineservise.controller;

import com.onlineservise.entity.Service;
import com.onlineservise.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping
    public List<Service> getAllServices() {

        return serviceService.getAllServices();
    }
}
