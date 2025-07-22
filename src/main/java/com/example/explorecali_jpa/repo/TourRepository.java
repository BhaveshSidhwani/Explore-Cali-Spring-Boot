package com.example.explorecali_jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.explorecali_jpa.model.Difficulty;
import com.example.explorecali_jpa.model.Tour;

public interface TourRepository extends JpaRepository<Tour, Integer> {
    List<Tour> findAllByDifficulty(Difficulty difficulty);
    List<Tour> findAllByTourPackageCode(String packageCode);
}
