package com.onlineservise.repository.impl;

import com.onlineservise.entity.*;
import com.onlineservise.repository.ServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcServiceOrderRepository implements ServiceOrderRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Service> serviceRowMapper = (rs, rowNum) -> {
        Service service = new Service();
        service.setId(rs.getLong("id"));
        service.setName(rs.getString("name"));
        service.setPrice(rs.getDouble("price"));
        return service;
    };

    private final RowMapper<ServiceOrder> orderRowMapper = (rs, rowNum) -> {
        ServiceOrder order = new ServiceOrder();
        order.setId(rs.getLong("id"));
        order.setStatus(rs.getString("status"));
        Timestamp timestamp = rs.getTimestamp("order_date");
        if (timestamp != null) {
            order.setOrderDate(timestamp.toLocalDateTime());
        }

        Client client = new Client();
        client.setId(rs.getLong("client_id"));
        order.setClient(client);

        Master master = new Master();
        master.setId(rs.getLong("master_id"));
        order.setMaster(master);

        return order;
    };

    @Override
    public List<ServiceOrder> findAll() {
        String sql = "SELECT * FROM service_order";
        List<ServiceOrder> orders = jdbcTemplate.query(sql, orderRowMapper);
        for (ServiceOrder order : orders) {
            // Fill related entities
            order.setClient(jdbcTemplate.queryForObject("SELECT * FROM client WHERE id = ?", (rs, row) -> {
                Client c = new Client(); c.setId(rs.getLong("id")); c.setName(rs.getString("name")); c.setPhone(rs.getString("phone")); return c;
            }, order.getClient().getId()));
            
            order.setMaster(jdbcTemplate.queryForObject("SELECT * FROM master WHERE id = ?", (rs, row) -> {
                Master m = new Master(); m.setId(rs.getLong("id")); m.setName(rs.getString("name")); m.setSpecialization(rs.getString("specialization")); return m;
            }, order.getMaster().getId()));
            
            order.setServices(findServicesByOrderId(order.getId()));
        }
        return orders;
    }

    @Override
    public Optional<ServiceOrder> findById(Long id) {
        try {
            ServiceOrder order = jdbcTemplate.queryForObject("SELECT * FROM service_order WHERE id = ?", orderRowMapper, id);
            if (order != null) {
                order.setServices(findServicesByOrderId(order.getId()));
            }
            return Optional.ofNullable(order);
        } catch (EmptyResultDataAccessException e) { return Optional.empty(); }
    }

    private List<Service> findServicesByOrderId(Long orderId) {
        return jdbcTemplate.query("SELECT s.id, s.name, s.price FROM services s JOIN order_services os ON s.id = os.service_id WHERE os.order_id = ?", serviceRowMapper, orderId);
    }

    @Override
    public ServiceOrder save(ServiceOrder order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO service_order (order_date, client_id, master_id, status) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            ps.setLong(2, order.getClient().getId());
            ps.setLong(3, order.getMaster().getId());
            ps.setString(4, order.getStatus());
            return ps;
        }, keyHolder);
        order.setId(keyHolder.getKey().longValue());
        saveOrderServices(order);
        return order;
    }

    @Override
    public void update(ServiceOrder order) {
        jdbcTemplate.update("UPDATE service_order SET order_date = ?, client_id = ?, master_id = ?, status = ? WHERE id = ?",
                Timestamp.valueOf(order.getOrderDate()), order.getClient().getId(), order.getMaster().getId(), order.getStatus(), order.getId());
        jdbcTemplate.update("DELETE FROM order_services WHERE order_id = ?", order.getId());
        saveOrderServices(order);
    }

    private void saveOrderServices(ServiceOrder order) {
        if (order.getServices() != null) {
            for (Service s : order.getServices()) {
                jdbcTemplate.update("INSERT INTO order_services (order_id, service_id) VALUES (?, ?)", order.getId(), s.getId());
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM service_order WHERE id = ?", id);
    }
}
