package com.onlineservise.service.impl;

import com.onlineservise.entity.ServiceOrder;
import com.onlineservise.repository.ServiceOrderRepository;
import com.onlineservise.service.ServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceOrderServiceImpl
        implements ServiceOrderService {

    private final ServiceOrderRepository repository;

    @Override
    public List<ServiceOrder> getAllOrders() {

        return repository.findAll();
    }
}
