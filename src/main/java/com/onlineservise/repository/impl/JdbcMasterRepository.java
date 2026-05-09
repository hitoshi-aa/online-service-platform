package com.onlineservise.repository.impl;

import com.onlineservise.entity.Master;
import com.onlineservise.repository.MasterRepository;
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
public class JdbcMasterRepository implements MasterRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Master> masterRowMapper = (rs, rowNum) -> {
        Master master = new Master();
        master.setId(rs.getLong("id"));
        master.setName(rs.getString("name"));
        master.setSpecialization(rs.getString("specialization"));
        master.setLogin(rs.getString("login"));
        master.setPassword(rs.getString("password"));
        return master;
    };

    @Override
    public List<Master> findAll() {
        return jdbcTemplate.query("SELECT * FROM master", masterRowMapper);
    }

    @Override
    public Optional<Master> findById(Long id) {
        try {
            Master master = jdbcTemplate.queryForObject("SELECT * FROM master WHERE id = ?", masterRowMapper, id);
            return Optional.ofNullable(master);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Master> findByLogin(String login) {
        try {
            Master master = jdbcTemplate.queryForObject("SELECT * FROM master WHERE login = ?", masterRowMapper, login);
            return Optional.ofNullable(master);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Master save(Master master) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO master (name, specialization, login, password) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, master.getName());
            ps.setString(2, master.getSpecialization());
            ps.setString(3, master.getLogin());
            ps.setString(4, master.getPassword());
            return ps;
        }, keyHolder);
        master.setId(keyHolder.getKey().longValue());
        return master;
    }

    @Override
    public void update(Master master) {
        jdbcTemplate.update("UPDATE master SET name = ?, specialization = ?, login = ?, password = ? WHERE id = ?",
                master.getName(), master.getSpecialization(), master.getLogin(), master.getPassword(), master.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM master WHERE id = ?", id);
    }
}
