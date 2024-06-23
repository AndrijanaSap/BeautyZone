package com.beautyzone.beautysalonapp.service;

import com.beautyzone.beautysalonapp.rest.dto.*;
import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ServiceService {

    List<ServiceWithCategoryDto> findAll() throws NoSuchElementException;

    List<ServiceWithEmployeesDto> getAllServicesWithEmployees() throws NoSuchElementException;

    ServiceWithCategoryDto findById(Integer id);

    ServiceWithEmployeesDto getServiceWithEmployeesById(Integer id);

    Integer addService(ServiceUpdateRequestDto request, MultipartFile img) throws IOException;

    void updateService(ServiceUpdateRequestDto serviceUpdateRequestDto, MultipartFile img) throws IOException;

    boolean deleteServiceById(Integer id);

    Optional<String> getExtensionByStringHandling(String filename);

    List<ServiceWithCategoryDto> getPopularServices() throws NoSuchElementException;
}
