package com.example.travel.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    private String userId;
    private String flightId;

    private double amountPaid;
    private LocalDateTime bookingTime;

    // ================= CANCELLATION =================
    private boolean cancelled;
    private CancellationReason cancellationReason;
    private LocalDateTime cancelledAt;

    // ================= REFUND =================
    private double refundAmount;
    private RefundStatus refundStatus;

    // 🔥 REFUND TRACKER TIMELINES
    private LocalDateTime refundInitiatedAt;
    private LocalDateTime refundCompletedAt;

    public Booking() {}

    // -------- GETTERS & SETTERS --------

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public CancellationReason getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(CancellationReason cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public RefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public LocalDateTime getRefundInitiatedAt() {
        return refundInitiatedAt;
    }

    public void setRefundInitiatedAt(LocalDateTime refundInitiatedAt) {
        this.refundInitiatedAt = refundInitiatedAt;
    }

    public LocalDateTime getRefundCompletedAt() {
        return refundCompletedAt;
    }

    public void setRefundCompletedAt(LocalDateTime refundCompletedAt) {
        this.refundCompletedAt = refundCompletedAt;
    }
}
