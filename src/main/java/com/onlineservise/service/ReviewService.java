package com.onlineservise.service;

import com.onlineservise.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    ReviewDTO getReviewById(Long id);
    ReviewDTO saveReview(ReviewDTO reviewDTO);
    void updateReview(ReviewDTO reviewDTO);
    void deleteReview(Long id);
}
