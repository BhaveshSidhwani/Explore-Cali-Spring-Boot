package com.example.explorecali_jpa;

import com.example.explorecali_jpa.business.TourPackageService;
import com.example.explorecali_jpa.business.TourService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExplorecaliJpaApplication implements CommandLineRunner {

	@Autowired
    private TourPackageService tourPackageService;
	
	@Autowired
    private TourService tourService;

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Persisted Packages = " + tourPackageService.total());
		System.out.println("Persisted Tours = " + tourService.total());
	}
}
