package com.onlineservise.repository;

import com.onlineservise.entity.Service;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    List<Service> findAll();
    Optional<Service> findById(Long id);
    Service save(Service service);
    void update(Service service);
    void deleteById(Long id);
}
