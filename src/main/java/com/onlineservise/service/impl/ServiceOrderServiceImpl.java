package com.onlineservise.service.impl;

import com.onlineservise.dto.ClientDTO;
import com.onlineservise.dto.MasterDTO;
import com.onlineservise.dto.ServiceDTO;
import com.onlineservise.dto.ServiceOrderDTO;
import com.onlineservise.entity.Client;
import com.onlineservise.entity.Master;
import com.onlineservise.entity.Service;
import com.onlineservise.entity.ServiceOrder;
import com.onlineservise.repository.ServiceOrderRepository;
import com.onlineservise.service.ServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
public class ServiceOrderServiceImpl implements ServiceOrderService {

    private final ServiceOrderRepository orderRepository;

    @Override
    public List<ServiceOrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceOrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public ServiceOrderDTO saveOrder(ServiceOrderDTO dto) {
        ServiceOrder order = convertToEntity(dto);
        return convertToDTO(orderRepository.save(order));
    }

    @Override
    public void updateOrder(ServiceOrderDTO dto) {
        orderRepository.update(convertToEntity(dto));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private ServiceOrderDTO convertToDTO(ServiceOrder order) {
        ServiceOrderDTO dto = ServiceOrderDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .build();
        
        if (order.getClient() != null) {
            dto.setClient(ClientDTO.builder()
                    .id(order.getClient().getId())
                    .name(order.getClient().getName())
                    .phone(order.getClient().getPhone())
                    .build());
        }
        
        if (order.getMaster() != null) {
            dto.setMaster(MasterDTO.builder()
                    .id(order.getMaster().getId())
                    .name(order.getMaster().getName())
                    .specialization(order.getMaster().getSpecialization())
                    .login(order.getMaster().getLogin())
                    .build());
        }
        
        if (order.getServices() != null) {
            dto.setServices(order.getServices().stream()
                    .map(s -> ServiceDTO.builder()
                            .id(s.getId())
                            .name(s.getName())
                            .price(s.getPrice())
                            .build())
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    private ServiceOrder convertToEntity(ServiceOrderDTO dto) {
        ServiceOrder order = new ServiceOrder();
        order.setId(dto.getId());
        order.setOrderDate(dto.getOrderDate());
        
        if (dto.getClient() != null) {
            Client client = new Client();
            client.setId(dto.getClient().getId());
            client.setName(dto.getClient().getName());
            client.setPhone(dto.getClient().getPhone());
            order.setClient(client);
        }
        
        if (dto.getMaster() != null) {
            Master master = new Master();
            master.setId(dto.getMaster().getId());
            master.setName(dto.getMaster().getName());
            master.setSpecialization(dto.getMaster().getSpecialization());
            master.setLogin(dto.getMaster().getLogin());
            order.setMaster(master);
        }
        
        if (dto.getServices() != null) {
            order.setServices(dto.getServices().stream()
                    .map(s -> {
                        Service service = new Service();
                        service.setId(s.getId());
                        service.setName(s.getName());
                        service.setPrice(s.getPrice());
                        return service;
                    })
                    .collect(Collectors.toList()));
        }
        
        return order;
    }
}
