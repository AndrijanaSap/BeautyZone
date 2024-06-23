package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.EmployeeRegisterRequest;
import com.beautyzone.beautysalonapp.rest.dto.EmployeeUpdateRequest;
import com.beautyzone.beautysalonapp.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        try {
            return ResponseEntity.ok(employeeServiceImpl.getAllEmployees());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @GetMapping("/{serviceId}")
    public ResponseEntity<?> getEmployeesByServiceId(@PathVariable Integer serviceId) {
        try {
            return ResponseEntity.ok(employeeServiceImpl.getEmployeesByServiceId(serviceId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer userId) {
        try {
            return ResponseEntity.ok(employeeServiceImpl.getEmployeeById(userId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Void> addEmployee(
            @RequestBody EmployeeRegisterRequest request
    ) {
        employeeServiceImpl.addEmployee(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeUpdateRequest employeeUpdateRequest){
        employeeServiceImpl.updateEmployee(employeeUpdateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteEmployee(@PathVariable Integer id) {
        return employeeServiceImpl.deleteEmployeeById(id);
    }
}