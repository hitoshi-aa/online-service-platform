package com.onlineservise.repository;

import com.onlineservise.entity.Master;
import java.util.List;
import java.util.Optional;

public interface MasterRepository {
    List<Master> findAll();
    Optional<Master> findById(Long id);
    Optional<Master> findByLogin(String login);
    Master save(Master master);
    void update(Master master);
    void deleteById(Long id);
}
