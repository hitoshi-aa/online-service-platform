package com.onlineservise.repository;

import com.onlineservise.entity.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    List<Review> findAll();
    Optional<Review> findById(Long id);
    Review save(Review review);
    void update(Review review);
    void deleteById(Long id);
}
