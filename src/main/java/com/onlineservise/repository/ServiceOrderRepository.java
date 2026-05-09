package com.onlineservise.repository;

import com.onlineservise.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOrderRepository
        extends JpaRepository<ServiceOrder, Long> {
}
