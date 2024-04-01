package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.EmployeeRegisterRequest;
import com.beautyzone.beautysalonapp.rest.dto.EmployeeUpdateRequest;
import com.beautyzone.beautysalonapp.rest.dto.ServiceUpdateRequestDto;
import com.beautyzone.beautysalonapp.service.impl.ServiceService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping
    public ResponseEntity<?> getAllServices() {
        try {
            return ResponseEntity.ok(serviceService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/with-employees")
    public ResponseEntity<?> getAllServicesWithEmployees() {
        try {
            return ResponseEntity.ok(serviceService.getAllServicesWithEmployees());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<?> getServiceById(@PathVariable Integer serviceId) {
        try {
            return ResponseEntity.ok(serviceService.findById(serviceId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/with-employees/{serviceId}")
    public ResponseEntity<?> getServiceWithEmployeesById(@PathVariable Integer serviceId) {
        try {
            return ResponseEntity.ok(serviceService.getServiceWithEmployeesById(serviceId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
//    @PostMapping("/add")
//    public ResponseEntity<Void> addService(
//            @RequestBody ServiceUpdateRequestDto serviceUpdateRequestDto
//    ) {
//        serviceService.addService(serviceUpdateRequestDto);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//    @PutMapping("/update")
//    public ResponseEntity<Void> updateService(@RequestBody ServiceUpdateRequestDto serviceUpdateRequestDto){
//        serviceService.updateService(serviceUpdateRequestDto);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addService(@RequestPart(value = "img", required = false) MultipartFile img,
                                        @RequestPart(value = "addDto") ServiceUpdateRequestDto serviceUpdateRequestDto) {
        try {
            return ResponseEntity.ok(serviceService.addService(serviceUpdateRequestDto, img));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateService(@RequestPart(value = "img", required = false) MultipartFile img,
                                           @RequestPart(value = "updateDto") ServiceUpdateRequestDto serviceUpdateRequestDto) {
        try {
            serviceService.updateService(serviceUpdateRequestDto, img);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteService(@PathVariable Integer id) {
        return serviceService.deleteServiceById(id);
    }
}