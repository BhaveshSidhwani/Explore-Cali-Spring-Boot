package com.example.explorecali_jpa.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.explorecali_jpa.business.TourRatingService;
import com.example.explorecali_jpa.model.TourRating;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private TourRatingService tourRatingService;

    public TourRatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    @GetMapping
    public List<RatingDto> getAllRatingsForTour(@PathVariable Integer tourId) {
        List<TourRating> tourRatings = tourRatingService.lookupRatings(tourId);
        return tourRatings.stream().map(RatingDto::new).toList();
    }

    @GetMapping("/average")
    public Map<String, Double> getAverage(@PathVariable Integer tourId) {
        return Map.of("average", tourRatingService.getAverageRating(tourId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(
        @PathVariable Integer tourId,
        @RequestBody @Valid RatingDto ratingDto
    ) {
        System.out.println("ratingDto: " + ratingDto);
        tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getRating(), ratingDto.getComment());
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManyTourRatings(@PathVariable int tourId, @RequestParam int rating, @RequestBody List<Integer> customers) {
        tourRatingService.rateMany(tourId, rating, customers);
    }

    @PutMapping
    public RatingDto updateWithPut(
        @PathVariable Integer tourId,
        @RequestBody @Valid RatingDto rating
    ) {
        return new RatingDto(tourRatingService.update(tourId, rating.getCustomerId(), rating.getRating(), rating.getComment()));
    }

    @PatchMapping
    public RatingDto updateWithPatch(
        @PathVariable Integer tourId,
        @RequestBody @Valid RatingDto rating
    ) {
        return new RatingDto(
            tourRatingService.updateSome(
                tourId,
                rating.getCustomerId(),
                Optional.ofNullable(rating.getRating()),
                Optional.ofNullable(rating.getComment())
            )
        );
    }

    @DeleteMapping("{customerId}")
    public void delete(
        @PathVariable Integer tourId,
        @PathVariable Integer customerId
    ) {
        tourRatingService.delete(tourId, customerId);
    }
}
