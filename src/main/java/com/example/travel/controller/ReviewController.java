package com.example.travel.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.model.Flight;
import com.example.travel.model.Hotel;
import com.example.travel.model.Reply;
import com.example.travel.model.Review;
import com.example.travel.model.ReviewTargetType;
import com.example.travel.repository.FlightRepository;
import com.example.travel.repository.HotelRepository;
import com.example.travel.repository.ReviewRepository;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    private final ReviewRepository reviewRepo;
    private final HotelRepository hotelRepo;
    private final FlightRepository flightRepo;

    public ReviewController(
            ReviewRepository reviewRepo,
            HotelRepository hotelRepo,
            FlightRepository flightRepo
    ) {
        this.reviewRepo = reviewRepo;
        this.hotelRepo = hotelRepo;
        this.flightRepo = flightRepo;
    }

    // ================= CREATE REVIEW =================
    @PostMapping
    public Review createReview(@RequestBody Review review) {

        Review saved = reviewRepo.save(review);

        // ⭐ UPDATE RATING AFTER REVIEW
        updateAverageRating(
                review.getTargetId(),
                review.getTargetType()
        );

        return saved;
    }

    // ================= GET REVIEWS (SORTED) =================
    @GetMapping
    public List<Review> getReviews(
            @RequestParam String targetId,
            @RequestParam ReviewTargetType targetType,
            @RequestParam(defaultValue = "newest") String sort
    ) {
        Sort sortOption = switch (sort) {
            case "helpful" -> Sort.by(Sort.Direction.DESC, "helpfulCount");
            case "rating" -> Sort.by(Sort.Direction.DESC, "rating");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };

        return reviewRepo.findByTargetIdAndTargetTypeAndRemovedFalse(
                targetId,
                targetType,
                sortOption
        );
    }

    // ================= MARK HELPFUL =================
    @PostMapping("/{id}/helpful")
    public Review markHelpful(@PathVariable String id) {
        Review r = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        r.setHelpfulCount(r.getHelpfulCount() + 1);
        return reviewRepo.save(r);
    }

    // ================= FLAG REVIEW =================
    @PostMapping("/{id}/flag")
    public Review flagReview(@PathVariable String id) {
        Review r = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        r.setFlagged(true);
        return reviewRepo.save(r);
    }

    // ================= ADD REPLY (USER / ADMIN) =================
    @PostMapping("/{reviewId}/reply")
    public Review addReply(
            @PathVariable String reviewId,
            @RequestBody Reply reply
    ) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.getReplies().add(reply);
        return reviewRepo.save(review);
    }

    // ================= ADMIN: REMOVE REVIEW =================
    @PostMapping("/{id}/remove")
    public void removeReview(@PathVariable String id) {
        Review r = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        r.setRemoved(true);
        reviewRepo.save(r);

        updateAverageRating(
                r.getTargetId(),
                r.getTargetType()
        );
    }

    // ================= RATING CALCULATION =================
    private void updateAverageRating(String targetId, ReviewTargetType type) {

        List<Review> reviews = reviewRepo
                .findByTargetIdAndTargetTypeAndRemovedFalse(
                        targetId,
                        type
                );

        if (reviews.isEmpty()) return;

        double avg = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);

        if (type == ReviewTargetType.HOTEL) {
            Hotel hotel = hotelRepo.findById(targetId).orElse(null);
            if (hotel != null) {
                hotel.setAverageRating(avg);
                hotel.setReviewCount(reviews.size());
                hotelRepo.save(hotel);
            }
        }

        if (type == ReviewTargetType.FLIGHT) {
            Flight flight = flightRepo.findById(targetId).orElse(null);
            if (flight != null) {
                flight.setAverageRating(avg);
                flight.setReviewCount(reviews.size());
                flightRepo.save(flight);
            }
        }
    }
}
