package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.AppointmentRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.AvailabilityRequestDto;
import com.beautyzone.beautysalonapp.service.impl.AppointmentService;
import com.beautyzone.beautysalonapp.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<?> findAllAppointments() {
        try {
            return ResponseEntity.ok(appointmentService.findAll());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/checkAvailability")
    public ResponseEntity<?> checkAvailability(@RequestBody AvailabilityRequestDto availabilityRequestDto){
        try {
            // TODO: If range periodFrom-periodTo(or periodTo minus periodFrom) is > 1 year, return validation error
            return ResponseEntity.ok(appointmentService.checkAvailability(availabilityRequestDto));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/createAppointment")
    public ResponseEntity<?> createAnAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto){
        try {
            return ResponseEntity.ok(appointmentService.createAppointment(appointmentRequestDto));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}