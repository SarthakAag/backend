package com.example.travel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.travel.model.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {

    // USER: get bookings by user
    List<Booking> findByUserId(String userId);

    // ADMIN: get only cancelled bookings
    List<Booking> findByCancelledTrue();
}
