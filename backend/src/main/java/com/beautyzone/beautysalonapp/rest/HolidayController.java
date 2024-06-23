package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.HolidayRequestDto;
import com.beautyzone.beautysalonapp.service.impl.HolidayServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayServiceImpl holidayServiceImpl;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(holidayServiceImpl.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @GetMapping("/getAllByEmployeeId/{employeeId}")
    public ResponseEntity<?> getAllByEmployeeId(@PathVariable Integer employeeId) {
        try {
            return ResponseEntity.ok(holidayServiceImpl.findAllByEmployeeId(employeeId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{holidayId}")
    public ResponseEntity<?> getHolidayById(@PathVariable Integer holidayId) {
        try {
            return ResponseEntity.ok(holidayServiceImpl.findById(holidayId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/createHoliday")
    public ResponseEntity<?> createAnHoliday(@RequestBody HolidayRequestDto requestDto){
        try {
            holidayServiceImpl.createHoliday(requestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/updateHoliday")
    public ResponseEntity<?> updateAnHoliday(@RequestBody HolidayRequestDto requestDto){
        try {
            holidayServiceImpl.updateHoliday(requestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteHoliday(@PathVariable Integer id) {
        return holidayServiceImpl.deleteHolidayById(id);
    }
}