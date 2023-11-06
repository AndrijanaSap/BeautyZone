package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.domain.Service;
import com.beautyzone.beautysalonapp.rest.dto.ServiceDto;
import com.beautyzone.beautysalonapp.service.impl.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping
    public ResponseEntity<?> findAllServices() {
        try {
            return ResponseEntity.ok(serviceService.findAll());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}