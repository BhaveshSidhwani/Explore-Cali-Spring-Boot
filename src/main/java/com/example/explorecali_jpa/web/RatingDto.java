package com.example.explorecali_jpa.web;

import com.example.explorecali_jpa.model.TourRating;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RatingDto {
    @Min(0)
    @Max(5)
    private Integer rating;

    @Size(max = 255)
    private String comment;
    
    @NotNull
    private Integer customerId;

    @JsonCreator
    public RatingDto(
        @JsonProperty("rating") Integer rating,
        @JsonProperty("comment") String comment,
        @JsonProperty("customerId") Integer customerId
    ) {
        this.rating = rating;
        this.comment = comment;
        this.customerId = customerId;
    }

    public RatingDto(TourRating tourRating) {
        this(tourRating.getRating(), tourRating.getComment(), tourRating.getCustomerId());
    }
}
