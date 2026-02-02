package com.example.travel.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.travel.model.Review;
import com.example.travel.model.ReviewTargetType;

public interface ReviewRepository extends MongoRepository<Review, String> {

    // ================= USER =================
    List<Review> findByTargetIdAndTargetTypeAndRemovedFalse(
            String targetId,
            ReviewTargetType targetType
    );

    List<Review> findByTargetIdAndTargetTypeAndRemovedFalse(
            String targetId,
            ReviewTargetType targetType,
            Sort sort
    );

    // ================= ADMIN =================
    List<Review> findByTargetIdAndTargetType(
            String targetId,
            ReviewTargetType targetType
    );

    List<Review> findByFlaggedTrueAndRemovedFalse();

    List<Review> findByRemovedTrue();
}
