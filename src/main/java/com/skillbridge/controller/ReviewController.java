package com.skillbridge.controller;

import com.skillbridge.entity.Review;
import com.skillbridge.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://skillbridge-sepia.vercel.app"
})
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestParam Long learnerId, @RequestParam Long workshopId, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.addReview(learnerId, workshopId, review));
    }

    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<Review>> getWorkshopReviews(@PathVariable Long workshopId) {
        return ResponseEntity.ok(reviewService.getReviewsForWorkshop(workshopId));
    }
}