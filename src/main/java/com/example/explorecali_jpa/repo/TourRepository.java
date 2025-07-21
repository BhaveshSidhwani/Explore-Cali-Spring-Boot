package com.example.explorecali_jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.explorecali_jpa.model.Tour;

public interface TourRepository extends JpaRepository<Tour, Integer> {

}
