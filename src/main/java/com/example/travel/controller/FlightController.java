package com.example.travel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.model.Flight;
import com.example.travel.repository.FlightRepository;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*") // allows frontend / Postman
public class FlightController {

    private final FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // ================= GET ALL FLIGHTS =================
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // ================= GET FLIGHT BY ID (✅ REQUIRED) =================
    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable String id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    // ================= SEARCH FLIGHTS =================
    // Examples:
    // /api/flights/search?source=Delhi&destination=Mumbai
    // /api/flights/search?source=Delhi&destination=Mumbai&date=2026-02-10
    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam(required = false) String date
    ) {
        if (date != null && !date.isEmpty()) {
            return flightRepository
                    .findBySourceAndDestinationAndDate(source, destination, date);
        }
        return flightRepository
                .findBySourceAndDestination(source, destination);
    }
}
