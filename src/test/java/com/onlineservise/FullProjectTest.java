package com.onlineservise;

import com.onlineservise.dto.MasterDTO;
import com.onlineservise.entity.Client;
import com.onlineservise.entity.Master;
import com.onlineservise.entity.Service;
import com.onlineservise.entity.ServiceOrder;
import com.onlineservise.repository.ClientRepository;
import com.onlineservise.repository.MasterRepository;
import com.onlineservise.repository.ServiceOrderRepository;
import com.onlineservise.repository.ServiceRepository;
import com.onlineservise.service.MasterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class FullProjectTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceOrderRepository orderRepository;

    @Autowired
    private MasterService masterService;

    @Test
    void testManyToManyRelationship() {
        // 1. Create client
        Client client = new Client();
        client.setName("Test Client");
        client.setPhone("+380991112233");
        client = clientRepository.save(client);

        // 2. Create master
        Master master = new Master();
        master.setName("Test Master");
        master.setSpecialization("Testing");
        master.setLogin("testmaster");
        master.setPassword("password");
        master = masterRepository.save(master);

        // 3. Get existing services
        List<Service> services = serviceRepository.findAll();
        Assertions.assertTrue(services.size() >= 2, "Should have at least 2 services in seed data");

        // 4. Create order with multiple services (N:M)
        ServiceOrder order = new ServiceOrder();
        order.setClient(client);
        order.setMaster(master);
        order.setOrderDate(LocalDateTime.now());
        order.getServices().add(services.get(0));
        order.getServices().add(services.get(1));
        
        ServiceOrder savedOrder = orderRepository.save(order);
        Assertions.assertNotNull(savedOrder.getId());

        // 5. Load and verify
        Optional<ServiceOrder> loadedOrderOpt = orderRepository.findById(savedOrder.getId());
        Assertions.assertTrue(loadedOrderOpt.isPresent());
        ServiceOrder loadedOrder = loadedOrderOpt.get();
        
        Assertions.assertEquals(2, loadedOrder.getServices().size(), "Order should have 2 services");
        Assertions.assertEquals(client.getName(), loadedOrder.getClient().getName());
        Assertions.assertEquals(master.getName(), loadedOrder.getMaster().getName());
    }

    @Test
    void testDatabaseAuthentication() {
        // 1. Register new master through service (encodes password)
        MasterDTO masterDTO = MasterDTO.builder()
                .name("Auth Master")
                .login("auth_test")
                .password("secret123")
                .specialization("Security")
                .build();
        
        masterService.saveMaster(masterDTO);

        // 2. Test successful authentication
        boolean success = masterService.authenticate("auth_test", "secret123");
        Assertions.assertTrue(success, "Should authenticate with correct password");

        // 3. Test failed authentication
        boolean failed = masterService.authenticate("auth_test", "wrong_password");
        Assertions.assertFalse(failed, "Should not authenticate with wrong password");
    }

    @Test
    void testSearchFunctionality() {
        Client client = new Client();
        client.setName("UniqueSearchName");
        client.setPhone("+380000000000");
        clientRepository.save(client);

        List<Client> results = clientRepository.findByNameContaining("UniqueSearch");
        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals("UniqueSearchName", results.get(0).getName());
    }
}
