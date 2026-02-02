package com.example.travel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.travel.model.Flight;

public interface FlightRepository extends MongoRepository<Flight, String> {

    List<Flight> findBySourceAndDestination(String source, String destination);

    List<Flight> findBySourceAndDestinationAndDate(
            String source,
            String destination,
            String date
    );
}
