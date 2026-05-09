package com.onlineservise.service;

import com.onlineservise.dto.ServiceOrderDTO;
import java.util.List;

public interface ServiceOrderService {
    List<ServiceOrderDTO> getAllOrders();
    ServiceOrderDTO getOrderById(Long id);
    ServiceOrderDTO saveOrder(ServiceOrderDTO orderDTO);
    void updateOrder(ServiceOrderDTO orderDTO);
    void deleteOrder(Long id);
}
