package com.onlineservise.repository;

import com.onlineservise.entity.ServiceOrder;
import java.util.List;
import java.util.Optional;

public interface ServiceOrderRepository {
    List<ServiceOrder> findAll();
    Optional<ServiceOrder> findById(Long id);
    ServiceOrder save(ServiceOrder order);
    void update(ServiceOrder order);
    void deleteById(Long id);
}
