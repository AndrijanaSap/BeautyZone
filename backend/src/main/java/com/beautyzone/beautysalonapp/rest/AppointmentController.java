package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.AppointmentCustomerDataUpdateRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.AppointmentRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.AvailabilityRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.UserDto;
import com.beautyzone.beautysalonapp.service.impl.AppointmentService;
import com.beautyzone.beautysalonapp.service.impl.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(appointmentService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/getAllByClientId/{clientId}")
    public ResponseEntity<?> getAllByClientId(@PathVariable Integer clientId) {
        try {
            return ResponseEntity.ok(appointmentService.findAllByClientId(clientId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/getAllByEmployeeId/{employeeId}")
    public ResponseEntity<?> getAllByEmployeeId(@PathVariable Integer employeeId) {
        try {
            return ResponseEntity.ok(appointmentService.findAllByEmployeeId(employeeId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Integer appointmentId) {
        try {
            return ResponseEntity.ok(appointmentService.findById(appointmentId));
        } catch (Exception ex) {
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

    @PostMapping("/updateAppointment")
    public ResponseEntity<?> updateAnAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto){
        try {
            return ResponseEntity.ok(appointmentService.updateAppointment(appointmentRequestDto));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping(value = "/calculateSignature", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateSignature(@RequestBody Map<String, String> params){
        try {
            String key = params.remove("key");
            return ResponseEntity.ok(appointmentService.calculateSignature(key, params));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/updateAppointmentCustomerData")
    public ResponseEntity<?> updateAppointmentCustomerData(@RequestBody AppointmentCustomerDataUpdateRequestDto updateRequestDto){
        try {
            appointmentService.update(updateRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteAppointment(@PathVariable Integer id) {
        return appointmentService.deleteAppointmentById(id);
    }
}