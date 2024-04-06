package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.rest.dto.CategoryUpdateRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.ServiceUpdateRequestDto;
import com.beautyzone.beautysalonapp.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @GetMapping("/with-services/{categoryId}")
    public ResponseEntity<?> getServiceWithEmployeesById(@PathVariable Integer categoryId) {
        try {
            return ResponseEntity.ok(categoryService.getCategoryWithServicesById(categoryId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping(value = "/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        try {
            categoryService.addCategory(categoryUpdateRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        try {
            categoryService.updateCategory(categoryUpdateRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCategory(@PathVariable Integer id) {
        return categoryService.deleteCategoryById(id);
    }
}