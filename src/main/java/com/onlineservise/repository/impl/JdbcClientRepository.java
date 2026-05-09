package com.onlineservise.repository.impl;

import com.onlineservise.entity.Client;
import com.onlineservise.repository.ClientRepository;
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
public class JdbcClientRepository implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Client> clientRowMapper = (rs, rowNum) -> {
        Client client = new Client();
        client.setId(rs.getLong("id"));
        client.setName(rs.getString("name"));
        client.setPhone(rs.getString("phone"));
        return client;
    };

    @Override
    public List<Client> findAll() {
        return jdbcTemplate.query("SELECT * FROM client", clientRowMapper);
    }

    @Override
    public Optional<Client> findById(Long id) {
        try {
            Client client = jdbcTemplate.queryForObject("SELECT * FROM client WHERE id = ?", clientRowMapper, id);
            return Optional.ofNullable(client);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Client> findByNameContaining(String name) {
        return jdbcTemplate.query("SELECT * FROM client WHERE name LIKE ?", clientRowMapper, "%" + name + "%");
    }

    @Override
    public Client save(Client client) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO client (name, phone) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getName());
            ps.setString(2, client.getPhone());
            return ps;
        }, keyHolder);
        client.setId(keyHolder.getKey().longValue());
        return client;
    }

    @Override
    public void update(Client client) {
        jdbcTemplate.update("UPDATE client SET name = ?, phone = ? WHERE id = ?",
                client.getName(), client.getPhone(), client.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM client WHERE id = ?", id);
    }
}
