package com.onlineservise.repository.impl;

import com.onlineservise.entity.Client;
import com.onlineservise.entity.Review;
import com.onlineservise.repository.ReviewRepository;
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
public class JdbcReviewRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Review> reviewRowMapper = (rs, rowNum) -> {
        Review review = new Review();
        review.setId(rs.getLong("review_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));

        Client client = new Client();
        client.setId(rs.getLong("client_id"));
        client.setName(rs.getString("client_name"));
        client.setPhone(rs.getString("client_phone"));
        review.setClient(client);

        return review;
    };

    @Override
    public List<Review> findAll() {
        String sql = "SELECT r.id as review_id, r.rating, r.comment, " +
                     "c.id as client_id, c.name as client_name, c.phone as client_phone " +
                     "FROM review r " +
                     "LEFT JOIN client c ON r.client_id = c.id";
        return jdbcTemplate.query(sql, reviewRowMapper);
    }

    @Override
    public Optional<Review> findById(Long id) {
        String sql = "SELECT r.id as review_id, r.rating, r.comment, " +
                     "c.id as client_id, c.name as client_name, c.phone as client_phone " +
                     "FROM review r " +
                     "LEFT JOIN client c ON r.client_id = c.id " +
                     "WHERE r.id = ?";
        try {
            Review review = jdbcTemplate.queryForObject(sql, reviewRowMapper, id);
            return Optional.ofNullable(review);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Review save(Review review) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO review (rating, comment, client_id) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            if (review.getClient() != null) {
                ps.setLong(3, review.getClient().getId());
            } else {
                ps.setNull(3, java.sql.Types.BIGINT);
            }
            return ps;
        }, keyHolder);
        review.setId(keyHolder.getKey().longValue());
        return review;
    }

    @Override
    public void update(Review review) {
        jdbcTemplate.update("UPDATE review SET rating = ?, comment = ?, client_id = ? WHERE id = ?",
                review.getRating(), review.getComment(),
                review.getClient() != null ? review.getClient().getId() : null,
                review.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM review WHERE id = ?", id);
    }
}
