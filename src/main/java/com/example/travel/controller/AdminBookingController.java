package com.example.travel.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.travel.model.Booking;
import com.example.travel.model.RefundStatus;
import com.example.travel.repository.BookingRepository;

@RestController
@RequestMapping("/api/admin/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminBookingController {

    private final BookingRepository bookingRepo;

    public AdminBookingController(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    // ================= ALL BOOKINGS =================
    @GetMapping(produces = "application/json")
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    // ================= CANCELLED BOOKINGS =================
    @GetMapping(value = "/cancelled", produces = "application/json")
    public List<Booking> getCancelledBookings() {
        return bookingRepo.findByCancelledTrue();
    }

    // ================= UPDATE REFUND STATUS =================
    @PostMapping("/{bookingId}/refund")
    public Booking updateRefundStatus(
            @PathVariable String bookingId,
            @RequestParam RefundStatus status
    ) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setRefundStatus(status);

        // 🔥 Track refund completion time
        if (status == RefundStatus.COMPLETED) {
            booking.setRefundCompletedAt(LocalDateTime.now());
        }

        return bookingRepo.save(booking);
    }
}
