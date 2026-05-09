package com.onlineservise.controller;

import com.onlineservise.entity.Review;
import com.onlineservise.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() {

        return reviewService.getAllReviews();
    }
}
