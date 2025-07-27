package com.example.explorecali_jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tour_rating")
@Data
public class TourRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(nullable = false)
    private Integer rating;

    @Column
    private String comment;

    protected TourRating() {
    }

    public TourRating(Tour tour, Integer customerId, Integer rating, String comment) {
        this.tour = tour;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }

    public TourRating(Tour tour, Integer customerId, Integer rating) {
        this.tour = tour;
        this.customerId = customerId;
        this.rating = rating;
    }
}
