package com.example.travel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.model.Hotel;
import com.example.travel.repository.HotelRepository;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "http://localhost:3000")
public class HotelController {

    private final HotelRepository hotelRepo;

    public HotelController(HotelRepository hotelRepo) {
        this.hotelRepo = hotelRepo;
    }

    // ================= GET ALL HOTELS =================
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    // ================= GET HOTEL BY ID (REQUIRED) =================
    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable String id) {
        return hotelRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    // ================= SEARCH BY CITY =================
    @GetMapping("/search")
    public List<Hotel> searchHotels(@RequestParam String city) {
        return hotelRepo.findByCityIgnoreCase(city);
    }
}
