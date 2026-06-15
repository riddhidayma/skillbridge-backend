package com.skillbridge.controller;

import com.skillbridge.dto.ReviewRequest;
import com.skillbridge.entity.Review;
import com.skillbridge.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody ReviewRequest request) {
        Review review = new Review();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return ResponseEntity.ok(reviewService.addReview(
                request.getLearnerId(),
                request.getWorkshopId(),
                review
        ));
    }

    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<Review>> getWorkshopReviews(@PathVariable Long workshopId) {
        return ResponseEntity.ok(reviewService.getReviewsForWorkshop(workshopId));
    }
}
