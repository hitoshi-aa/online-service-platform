package com.onlineservise.controller;

import com.onlineservise.entity.ServiceOrder;
import com.onlineservise.repository.ServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class ServiceOrderController {

    private final ServiceOrderRepository repository;

    @GetMapping
    public List<ServiceOrder> getAllOrders() {

        return repository.findAll();
    }
}
