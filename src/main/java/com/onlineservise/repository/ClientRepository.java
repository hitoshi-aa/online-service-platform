package com.onlineservise.repository;

import com.onlineservise.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository
        extends JpaRepository<Client, Long> {

}
