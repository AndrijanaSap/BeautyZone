package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.AvailabilityRequestDto;
import com.beautyzone.beautysalonapp.service.impl.TimeSlotServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/timeslots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotServiceImpl timeSlotServiceImpl;

//    @GetMapping
//    public ResponseEntity<?> findAllTimeSlots() {
//        try {
//            return ResponseEntity.ok(timeSlotService.findAll());
//        } catch (Exception ex){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//        }
//    }

    @PostMapping("/checkAvailability")
    public ResponseEntity<?> checkAvailability(@RequestBody AvailabilityRequestDto availabilityRequestDto){
        try {
            // TODO: If range periodFrom-periodTo(or periodTo minus periodFrom) is > 1 year, return validation error
            return ResponseEntity.ok(timeSlotServiceImpl.checkAvailability(availabilityRequestDto));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}