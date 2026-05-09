package com.onlineservise.controller;

import com.onlineservise.dto.ServiceOrderDTO;
import com.onlineservise.service.ServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class ServiceOrderController {

    private final ServiceOrderService orderService;

    @GetMapping
    public List<ServiceOrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ServiceOrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public ServiceOrderDTO createOrder(@RequestBody ServiceOrderDTO orderDTO) {
        return orderService.saveOrder(orderDTO);
    }

    @PutMapping("/{id}")
    public void updateOrder(@PathVariable Long id, @RequestBody ServiceOrderDTO orderDTO) {
        orderDTO.setId(id);
        orderService.updateOrder(orderDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
