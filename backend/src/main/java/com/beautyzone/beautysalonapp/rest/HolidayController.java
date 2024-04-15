package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.HolidayRequestDto;
import com.beautyzone.beautysalonapp.service.impl.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(holidayService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @GetMapping("/getAllByEmployeeId/{employeeId}")
    public ResponseEntity<?> getAllByEmployeeId(@PathVariable Integer employeeId) {
        try {
            return ResponseEntity.ok(holidayService.findAllByEmployeeId(employeeId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{holidayId}")
    public ResponseEntity<?> getHolidayById(@PathVariable Integer holidayId) {
        try {
            return ResponseEntity.ok(holidayService.findById(holidayId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/createHoliday")
    public ResponseEntity<?> createAnHoliday(@RequestBody HolidayRequestDto requestDto){
        try {
            holidayService.createHoliday(requestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/updateHoliday")
    public ResponseEntity<?> updateAnHoliday(@RequestBody HolidayRequestDto requestDto){
        try {
            holidayService.updateHoliday(requestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteHoliday(@PathVariable Integer id) {
        return holidayService.deleteHolidayById(id);
    }
}