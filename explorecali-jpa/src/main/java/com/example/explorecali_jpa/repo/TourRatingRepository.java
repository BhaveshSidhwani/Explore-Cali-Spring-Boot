package com.example.explorecali_jpa.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.explorecali_jpa.model.TourRating;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends JpaRepository<TourRating, Integer> {
    List<TourRating> findByTourId(Integer tourId);
    Optional<TourRating> findByTourIdAndCustomerId(Integer tourId, Integer customerId);
}
