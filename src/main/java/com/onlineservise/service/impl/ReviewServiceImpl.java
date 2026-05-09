package com.onlineservise.service.impl;

import com.onlineservise.entity.Review;
import com.onlineservise.repository.ReviewRepository;
import com.onlineservise.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews() {

        return reviewRepository.findAll();
    }
}
