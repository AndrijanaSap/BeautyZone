package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.ShiftRequestDto;
import com.beautyzone.beautysalonapp.service.impl.ShiftServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/shifts")
@RequiredArgsConstructor
@Slf4j
public class ShiftController {

    private final ShiftServiceImpl shiftServiceImpl;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(shiftServiceImpl.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @GetMapping("/getAllByEmployeeId/{employeeId}")
    public ResponseEntity<?> getAllByEmployeeId(@PathVariable Integer employeeId) {
        try {
            return ResponseEntity.ok(shiftServiceImpl.findAllByEmployeeId(employeeId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/getAllByEmployeeIdWithHolidays/{employeeId}")
    public ResponseEntity<?> getAllWithHolidays(@PathVariable Integer employeeId) {
        try {
            return ResponseEntity.ok(shiftServiceImpl.getAllWithHolidays(employeeId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{shiftId}")
    public ResponseEntity<?> getShiftById(@PathVariable Integer shiftId) {
        try {
            return ResponseEntity.ok(shiftServiceImpl.findById(shiftId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/createShift")
    public ResponseEntity<?> createAnShift(@RequestBody ShiftRequestDto requestDto){
        try {
            shiftServiceImpl.createShift(requestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/updateShift")
    public ResponseEntity<?> updateAnShift(@RequestBody ShiftRequestDto requestDto){
        try {
            shiftServiceImpl.updateShift(requestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteShift(@PathVariable Integer id) {
        return shiftServiceImpl.deleteShiftById(id);
    }
}