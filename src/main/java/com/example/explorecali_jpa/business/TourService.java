package com.example.explorecali_jpa.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.explorecali_jpa.model.Difficulty;
import com.example.explorecali_jpa.model.Region;
import com.example.explorecali_jpa.model.Tour;
import com.example.explorecali_jpa.model.TourPackage;
import com.example.explorecali_jpa.repo.TourPackageRepository;
import com.example.explorecali_jpa.repo.TourRepository;

@Service
public class TourService {
    private TourPackageRepository tourPackageRepository;
    private TourRepository tourRepository;

    public TourService(TourPackageRepository tourPackageRepository, TourRepository tourRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }

    public Tour createTour(
        String tourPackageName,
        String title,
        String description,
        String blurb,
        Integer price,
        String duration,
        String bullets,
        String keywords,
        Difficulty difficulty,
        Region region
    ) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
            .orElseThrow(() -> new RuntimeException("Tour package not found for id" + tourPackageName));

        return tourRepository.save(
            new Tour(
                title, description, blurb, 
                price, duration, bullets, 
                keywords, tourPackage, difficulty, region
            )
        );
    }

    public List<Tour> lookupByDifficulty(Difficulty difficulty) {
        return tourRepository.findAllByDifficulty(difficulty);
    }

    public List<Tour> lookupByPackage(String tourPackageCode) {
        return tourRepository.findAllByTourPackageCode(tourPackageCode);
    }

    public long total() {
        return tourRepository.count();
    }
}
