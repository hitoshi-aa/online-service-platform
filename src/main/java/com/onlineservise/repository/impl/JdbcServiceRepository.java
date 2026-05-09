package com.onlineservise.repository.impl;

import com.onlineservise.entity.Service;
import com.onlineservise.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcServiceRepository implements ServiceRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Service> serviceRowMapper = (rs, rowNum) -> {
        Service service = new Service();
        service.setId(rs.getLong("id"));
        service.setName(rs.getString("name"));
        service.setPrice(rs.getDouble("price"));
        return service;
    };

    @Override
    public List<Service> findAll() {
        return jdbcTemplate.query("SELECT * FROM services", serviceRowMapper);
    }

    @Override
    public Optional<Service> findById(Long id) {
        try {
            Service service = jdbcTemplate.queryForObject("SELECT * FROM services WHERE id = ?", serviceRowMapper, id);
            return Optional.ofNullable(service);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Service save(Service service) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO services (name, price) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getName());
            ps.setDouble(2, service.getPrice());
            return ps;
        }, keyHolder);
        service.setId(keyHolder.getKey().longValue());
        return service;
    }

    @Override
    public void update(Service service) {
        jdbcTemplate.update("UPDATE services SET name = ?, price = ? WHERE id = ?",
                service.getName(), service.getPrice(), service.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM services WHERE id = ?", id);
    }
}
