package com.onlineservise.repository;

import com.onlineservise.entity.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void shouldReturnAllReviews() {

        List<Review> reviews = reviewRepository.findAll();

        Assertions.assertFalse(reviews.isEmpty());

        System.out.println("===== REVIEW TEST =====");

        for (Review review : reviews) {
            System.out.println(review.getComment());
        }
    }
}