package com.example.travel.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.model.Booking;
import com.example.travel.model.CancellationReason;
import com.example.travel.model.RefundStatus;
import com.example.travel.repository.BookingRepository;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    private final BookingRepository bookingRepo;

    public BookingController(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    // ================= CREATE BOOKING =================
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Booking createBooking(@RequestBody Booking booking) {

        booking.setBookingTime(LocalDateTime.now());
        booking.setCancelled(false);
        booking.setRefundStatus(RefundStatus.NOT_APPLICABLE);
        booking.setRefundAmount(0);

        return bookingRepo.save(booking);
    }

    // ================= GET BOOKINGS BY USER =================
    @GetMapping(value = "/user/{userId}", produces = "application/json")
    public List<Booking> getUserBookings(@PathVariable String userId) {
        return bookingRepo.findByUserId(userId);
    }

    // ================= CANCEL BOOKING =================
    @PostMapping("/{bookingId}/cancel")
    public Booking cancelBooking(
            @PathVariable String bookingId,
            @RequestParam CancellationReason reason
    ) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.isCancelled()) {
            throw new RuntimeException("Booking already cancelled");
        }

        booking.setCancelled(true);
        booking.setCancellationReason(reason);
        booking.setCancelledAt(LocalDateTime.now());

        // ⏱️ Refund calculation
        long hours = Duration.between(
                booking.getBookingTime(),
                LocalDateTime.now()
        ).toHours();

        if (hours <= 24) {
            booking.setRefundAmount(booking.getAmountPaid() * 0.5);
        } else {
            booking.setRefundAmount(0);
        }

        booking.setRefundStatus(RefundStatus.PENDING);

        return bookingRepo.save(booking);
    }
}
