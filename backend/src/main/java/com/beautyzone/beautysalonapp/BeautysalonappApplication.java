package com.beautyzone.beautysalonapp;

import com.beautyzone.beautysalonapp.service.impl.FirstTimeSlotPopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BeautysalonappApplication {
	@Autowired
	private FirstTimeSlotPopulationService firstTimeSlotPopulationService;

	public static void main(String[] args) {
		SpringApplication.run(BeautysalonappApplication.class, args);
	}
	@Bean
	public CommandLineRunner init() {
		return args -> {
			// Run the one-time timeslot population during application startup
//			firstTimeSlotPopulationService.prepopulateTimeSlotsForOneYear();
		};
	}
}
