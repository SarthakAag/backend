package com.example.travel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.travel.model.Hotel;

public interface HotelRepository extends MongoRepository<Hotel, String> {

    List<Hotel> findByCityIgnoreCase(String city);
}
