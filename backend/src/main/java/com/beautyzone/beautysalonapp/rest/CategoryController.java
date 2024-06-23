package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.CategoryUpdateRequestDto;
import com.beautyzone.beautysalonapp.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @GetMapping("/with-services")
    public ResponseEntity<?> findAllCategoriesWithServices() {
        try {
            return ResponseEntity.ok(categoryServiceImpl.getAllWithServices());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAllCategories() {
        try {
            return ResponseEntity.ok(categoryServiceImpl.getAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/with-services/{categoryId}")
    public ResponseEntity<?> getServiceWithEmployeesById(@PathVariable Integer categoryId) {
        try {
            return ResponseEntity.ok(categoryServiceImpl.getCategoryWithServicesById(categoryId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping(value = "/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        try {
            categoryServiceImpl.addCategory(categoryUpdateRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        try {
            categoryServiceImpl.updateCategory(categoryUpdateRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCategory(@PathVariable Integer id) {
        return categoryServiceImpl.deleteCategoryById(id);
    }
}