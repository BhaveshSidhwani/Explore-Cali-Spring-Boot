package com.example.explorecali_jpa.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.explorecali_jpa.model.Difficulty;
import com.example.explorecali_jpa.model.Region;
import com.example.explorecali_jpa.model.Tour;
import com.example.explorecali_jpa.model.TourPackage;
import com.example.explorecali_jpa.repo.TourPackageRepository;
import com.example.explorecali_jpa.repo.TourRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
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
        log.info("create tour {} for package {}", title, tourPackageName);
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
        log.info("lookup tours by difficulty {}", difficulty);
        return tourRepository.findAllByDifficulty(difficulty);
    }

    public List<Tour> lookupByPackage(String tourPackageCode) {
        log.info("lookup tour by code {}", tourPackageCode);
        return tourRepository.findAllByTourPackageCode(tourPackageCode);
    }

    public long total() {
        log.info("get tour count");
        return tourRepository.count();
    }
}
