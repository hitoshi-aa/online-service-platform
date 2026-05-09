package com.onlineservise.service.impl;

import com.onlineservise.dto.ClientDTO;
import com.onlineservise.dto.ReviewDTO;
import com.onlineservise.entity.Client;
import com.onlineservise.entity.Review;
import com.onlineservise.repository.ReviewRepository;
import com.onlineservise.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO dto) {
        Review review = convertToEntity(dto);
        return convertToDTO(reviewRepository.save(review));
    }

    @Override
    public void updateReview(ReviewDTO dto) {
        reviewRepository.update(convertToEntity(dto));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = ReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
        if (review.getClient() != null) {
            dto.setClient(ClientDTO.builder()
                    .id(review.getClient().getId())
                    .name(review.getClient().getName())
                    .phone(review.getClient().getPhone())
                    .build());
        }
        return dto;
    }

    private Review convertToEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        if (dto.getClient() != null) {
            Client client = new Client();
            client.setId(dto.getClient().getId());
            client.setName(dto.getClient().getName());
            client.setPhone(dto.getClient().getPhone());
            review.setClient(client);
        }
        return review;
    }
}
