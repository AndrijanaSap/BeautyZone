package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.ShiftRequestDto;
import com.beautyzone.beautysalonapp.service.impl.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/shifts")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(shiftService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @GetMapping("/getAllByEmployeeId/{employeeId}")
    public ResponseEntity<?> getAllByEmployeeId(@PathVariable Integer employeeId) {
        try {
            return ResponseEntity.ok(shiftService.findAllByEmployeeId(employeeId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{shiftId}")
    public ResponseEntity<?> getShiftById(@PathVariable Integer shiftId) {
        try {
            return ResponseEntity.ok(shiftService.findById(shiftId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/createShift")
    public ResponseEntity<?> createAnShift(@RequestBody ShiftRequestDto requestDto){
        try {
            shiftService.createShift(requestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/updateShift")
    public ResponseEntity<?> updateAnShift(@RequestBody ShiftRequestDto requestDto){
        try {
            shiftService.updateShift(requestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteShift(@PathVariable Integer id) {
        return shiftService.deleteShiftById(id);
    }
}