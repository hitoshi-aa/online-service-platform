package com.onlineservise.repository;

import com.onlineservise.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepository
        extends JpaRepository<Master, Long> {

}
