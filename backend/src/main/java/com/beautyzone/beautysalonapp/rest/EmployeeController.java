package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.auth.ChangePasswordRequest;
import com.beautyzone.beautysalonapp.domain.Category;
import com.beautyzone.beautysalonapp.service.impl.CategoryService;
import com.beautyzone.beautysalonapp.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> findAllCategories() {
        try {
            return ResponseEntity.ok(categoryService.findAll());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}