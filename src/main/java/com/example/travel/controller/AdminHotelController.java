package com.example.travel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.model.Hotel;
import com.example.travel.model.Review;
import com.example.travel.model.ReviewTargetType;
import com.example.travel.repository.HotelRepository;
import com.example.travel.repository.ReviewRepository;

@RestController
@RequestMapping("/api/admin/hotels")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminHotelController {

    private final HotelRepository hotelRepo;
    private final ReviewRepository reviewRepo;

    public AdminHotelController(
            HotelRepository hotelRepo,
            ReviewRepository reviewRepo
    ) {
        this.hotelRepo = hotelRepo;
        this.reviewRepo = reviewRepo;
    }

    // ================= ADD HOTEL =================
    @PostMapping
    public Hotel addHotel(@RequestBody Hotel hotel) {
        return hotelRepo.save(hotel);
    }

    // ================= GET ALL HOTELS =================
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    // ================= DELETE HOTEL =================
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable String id) {
        hotelRepo.deleteById(id);
    }

    // ================= GET HOTEL REVIEWS (ADMIN) =================
    @GetMapping("/{hotelId}/reviews")
    public List<Review> getHotelReviews(@PathVariable String hotelId) {

        return reviewRepo.findByTargetIdAndTargetType(
                hotelId,
                ReviewTargetType.HOTEL
        );
    }
}
