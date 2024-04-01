package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/with-services")
    public ResponseEntity<?> findAllCategoriesWithServices() {
        try {
            return ResponseEntity.ok(categoryService.getAllWithServices());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAllCategories() {
        try {
            return ResponseEntity.ok(categoryService.getAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}