package com.onlineservise.repository;

import com.onlineservise.entity.Master;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MasterRepositoryTest {

    @Autowired
    private MasterRepository masterRepository;

    @Test
    void shouldReturnAllMasters() {

        List<Master> masters = masterRepository.findAll();

        Assertions.assertFalse(masters.isEmpty());

        System.out.println("===== MASTER TEST =====");

        for (Master master : masters) {
            System.out.println(master.getName());
        }
    }
}
