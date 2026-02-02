package com.example.travel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.model.Flight;
import com.example.travel.repository.FlightRepository;

@RestController
@RequestMapping("/api/admin/flights")
@CrossOrigin(origins = "*")
public class AdminFlightController {

    private final FlightRepository flightRepository;

    public AdminFlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // ✅ ADD flight (ADMIN)
    @PostMapping(consumes = "application/json")
    public Flight addFlight(@RequestBody Flight flight) {
        // ❌ DO NOT set ID manually
        return flightRepository.save(flight);
    }

    // ✅ VIEW all flights (ADMIN)
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // ✅ DELETE flight (ADMIN)
    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable String id) {
        flightRepository.deleteById(id);
    }

    // ✅ UPDATE flight (ADMIN)
    @PutMapping("/{id}")
    public Flight updateFlight(
            @PathVariable String id,
            @RequestBody Flight updatedFlight
    ) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setFlightNumber(updatedFlight.getFlightNumber());
        flight.setSource(updatedFlight.getSource());
        flight.setDestination(updatedFlight.getDestination());
        flight.setDate(updatedFlight.getDate());
        flight.setPrice(updatedFlight.getPrice());

        return flightRepository.save(flight);
    }
}
