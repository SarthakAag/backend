package com.example.travel.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    // ================= USER =================
    private String userId;
    private String userName;

    // ================= TARGET =================
    private String targetId;            // hotelId or flightId
    private ReviewTargetType targetType; // HOTEL / FLIGHT

    // ================= CONTENT =================
    private int rating;                 // 1–5
    private String comment;

    // ================= PHOTOS =================
    private List<String> photos = new ArrayList<>();

    // ================= REPLIES =================
    private List<Reply> replies = new ArrayList<>();

    // ================= INTERACTIONS =================
    private int helpfulCount;

    // ================= MODERATION =================
    private boolean flagged;
    private boolean removed;

    // ================= TIMESTAMP =================
    private LocalDateTime createdAt;

    // ================= CONSTRUCTOR =================
    public Review() {
        this.photos = new ArrayList<>();
        this.replies = new ArrayList<>();
        this.helpfulCount = 0;
        this.flagged = false;
        this.removed = false;
        this.createdAt = LocalDateTime.now();
    }

    // ================= GETTERS & SETTERS =================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // ---------- USER ----------
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // ---------- TARGET ----------
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public ReviewTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(ReviewTargetType targetType) {
        this.targetType = targetType;
    }

    // ---------- CONTENT ----------
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // ---------- PHOTOS ----------
    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    // ---------- REPLIES ----------
    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    // ---------- INTERACTIONS ----------
    public int getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(int helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

    // ---------- MODERATION ----------
    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    // ---------- TIMESTAMP ----------
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
