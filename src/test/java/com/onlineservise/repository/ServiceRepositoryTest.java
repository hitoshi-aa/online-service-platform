package com.onlineservise.repository;

import com.onlineservise.entity.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceRepositoryTest {

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    void shouldReturnAllServices() {

        List<Service> services = serviceRepository.findAll();

        Assertions.assertFalse(services.isEmpty());

        System.out.println("===== SERVICE TEST =====");

        for (Service service : services) {
            System.out.println(service.getName());
        }
    }
}
