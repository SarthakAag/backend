package com.example.travel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.model.Review;
import com.example.travel.repository.ReviewRepository;

@RestController
@RequestMapping("/api/admin/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminReviewController {

    private final ReviewRepository reviewRepo;

    public AdminReviewController(ReviewRepository reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    // ================= ALL REVIEWS =================
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    // ================= FLAGGED REVIEWS (NOT REMOVED) =================
    @GetMapping("/flagged")
    public List<Review> getFlaggedReviews() {
        return reviewRepo.findByFlaggedTrueAndRemovedFalse();
    }

    // ================= REMOVED REVIEWS =================
    @GetMapping("/removed")
    public List<Review> getRemovedReviews() {
        return reviewRepo.findByRemovedTrue();
    }

    // ================= REMOVE REVIEW =================
    @PostMapping("/{id}/remove")
    public Review removeReview(@PathVariable String id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRemoved(true);
        return reviewRepo.save(review);
    }

    // ================= RESTORE REVIEW =================
    @PostMapping("/{id}/restore")
    public Review restoreReview(@PathVariable String id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRemoved(false);
        review.setFlagged(false);
        return reviewRepo.save(review);
    }
}
