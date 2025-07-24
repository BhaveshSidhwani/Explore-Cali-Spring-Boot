package com.example.explorecali_jpa.business;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.stereotype.Service;

import com.example.explorecali_jpa.model.Tour;
import com.example.explorecali_jpa.model.TourRating;
import com.example.explorecali_jpa.repo.TourRatingRepository;
import com.example.explorecali_jpa.repo.TourRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class TourRatingService {
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    public TourRating createNew(Integer tourId, Integer customerId, Integer rating, String comment) {
        log.info("create a tour {} and customer {}", tourId, customerId);
        return tourRatingRepository.save(new TourRating(verifyTour(tourId), customerId, rating, comment));
    }

    public Optional<TourRating> lookupTatingById(Integer ratingId) {
        log.info("lookup rating by id {}", ratingId);
        return tourRatingRepository.findById(ratingId);
    }

    public List<TourRating> lookupAll() {
        log.info("lookup all tour ratings");
        return tourRatingRepository.findAll();
    }

    public List<TourRating> lookupRatings(Integer tourId) {
        log.info("lookup ratings for tour {}", tourId);
        return tourRatingRepository.findByTourId(verifyTour(tourId).getId());
    }

    public TourRating update(int tourId, Integer customerId, Integer rating, String comment) throws NoSuchElementException {
        log.info("update tour {} customer {}", tourId, customerId);
        TourRating tourRating = verifyTourRating(tourId, customerId);
        tourRating.setRating(rating);
        tourRating.setComment(comment);
        return tourRatingRepository.save(tourRating);
    }

    public TourRating updateSome(int tourId, Integer customerId, Optional<Integer> rating, Optional<String> comment) throws NoSuchElementException {
        log.info("update some of tour {} customer {}", tourId, customerId);
        TourRating tourRating = verifyTourRating(tourId, customerId);
        rating.ifPresent(s -> tourRating.setRating(s));
        comment.ifPresent(c -> tourRating.setComment(c));
        return tourRatingRepository.save(tourRating);
    }

    public void delete(int tourId, Integer customerId) throws NoSuchElementException {
        log.info("delete rating for tour {} customer {}", tourId, customerId);
        TourRating tourRating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(tourRating);
    }

    public Double getAverageRating(int tourId) throws NoSuchElementException {
        log.info("lookup average rating for tour {}", tourId);
        List<TourRating> tourRatings = tourRatingRepository.findByTourId(verifyTour(tourId).getId());
        OptionalDouble average = tourRatings.stream().mapToInt((rating) -> rating.getRating()).average();
        return average.isPresent() ? average.getAsDouble() : null;
    }

    public void rateMany(int tourId,  int rating, List<Integer> customers) {
        log.info("create ratings in batch for tour {} rating {} customers {}", tourId, rating, customers);
        Tour tour = verifyTour(tourId);
        for (Integer c : customers) {
        if (tourRatingRepository.findByTourIdAndCustomerId(tourId, c).isPresent()) {
            throw new ConstraintViolationException("Unable to create duplicate ratings", null);
        }
        tourRatingRepository.save(new TourRating(tour, c, rating));
        }
    }

    private Tour verifyTour(Integer tourId) {
        return tourRepository.findById(tourId)
            .orElseThrow(() -> new NoSuchElementException("Tour does not exist for tourId: " + tourId));
    }

    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
            .orElseThrow(() -> new NoSuchElementException("Tour-Rating pair for request("
                + tourId + " for customer" + customerId));
    }
}
