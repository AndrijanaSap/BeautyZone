package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.repository.CategoryRepository;
import com.beautyzone.beautysalonapp.repository.EmployeeRepository;
import com.beautyzone.beautysalonapp.repository.ServiceRepository;
import com.beautyzone.beautysalonapp.rest.dto.*;
import com.beautyzone.beautysalonapp.rest.mapper.ServiceMapper;

import com.beautyzone.beautysalonapp.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;
    private final CategoryRepository categoryRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public List<ServiceWithCategoryDto> findAll() throws NoSuchElementException {
        List<com.beautyzone.beautysalonapp.domain.Service> services = serviceRepository.findAll();
        if (services.isEmpty()) {
            throw new NoSuchElementException("No service found!");
        }
        return serviceMapper.servicesToServiceWithCategoryDtos(services);
    }
    @Override
    public List<ServiceWithEmployeesDto> getAllServicesWithEmployees() throws NoSuchElementException {
        List<com.beautyzone.beautysalonapp.domain.Service> services = serviceRepository.findAll();
        if (services.isEmpty()) {
            throw new NoSuchElementException("No service found!");
        }
        return serviceMapper.servicesToServiceWithEmployeeesDtos(services);
    }
    @Override
    public ServiceWithCategoryDto findById(Integer id) {
        com.beautyzone.beautysalonapp.domain.Service service = serviceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Service not found with id: " + id));
        return serviceMapper.serviceToServiceWithCategoryDto(service);
    }
    @Override
    public ServiceWithEmployeesDto getServiceWithEmployeesById(Integer id) {
        com.beautyzone.beautysalonapp.domain.Service service = serviceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Service not found with id: " + id));
        return serviceMapper.serviceToServiceWithEmployeeesDto(service);
    }
    @Override
    public Integer addService(ServiceUpdateRequestDto request, MultipartFile img) throws IOException {
        ;
        var service = com.beautyzone.beautysalonapp.domain.Service.builder()
                .name(request.getName())
                .price(request.getPrice())
                .durationInMinutes(request.getDurationInMinutes())
                .imgPath(request.getImgPath())
                .description(request.getDescription())
                .category(request.getCategoryId() != null ?
                        categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NoSuchElementException("Category with id: " + request.getCategoryId() + " doesnt exists.")) : null)
                .employees(employeeRepository.findAllById(request.getEmployees()))
                .build();

        serviceRepository.save(service);

        if (img != null) {
            service.setImgPath("/assets/img/services/" + "service-" + service.getId() + "." + getExtensionByStringHandling(img.getOriginalFilename()).get());

            // Save image to frontend/src/assets/img/services as "service-${serviceId}.extension"
            Path filePath = Paths.get("").toAbsolutePath().getParent().resolve("frontend\\src\\assets\\img\\services")
                    .resolve("service-" + service.getId() + "." + getExtensionByStringHandling(img.getOriginalFilename()).get());
            img.transferTo(filePath.toFile());
        }
        serviceRepository.save(service);

        return service.getId();
    }
    @Override
    public void updateService(ServiceUpdateRequestDto serviceUpdateRequestDto, MultipartFile img) throws IOException {
        com.beautyzone.beautysalonapp.domain.Service service = serviceRepository.findById(serviceUpdateRequestDto.getId()).orElseThrow(() -> new UsernameNotFoundException("Service not found"));
        service.setName(serviceUpdateRequestDto.getName());
        service.setPrice(serviceUpdateRequestDto.getPrice());
        service.setDurationInMinutes(serviceUpdateRequestDto.getDurationInMinutes());
        service.setDescription(serviceUpdateRequestDto.getDescription());
        service.setCategory(categoryRepository.findById(serviceUpdateRequestDto.getCategoryId()).orElseThrow(() -> new NoSuchElementException("Category not found")));
        service.setEmployees(employeeRepository.findAllById(serviceUpdateRequestDto.getEmployees()));

        if (img != null) {
            service.setImgPath("/assets/img/services/" + "service-" + serviceUpdateRequestDto.getId() + "." + getExtensionByStringHandling(img.getOriginalFilename()).get());

            // Save image to frontend/src/assets/img/services as "service-${serviceId}.extension"
            Path filePath = Paths.get("").toAbsolutePath().getParent().resolve("frontend\\src\\assets\\img\\services")
                    .resolve("service-" + serviceUpdateRequestDto.getId() + "." + getExtensionByStringHandling(img.getOriginalFilename()).get());
            img.transferTo(filePath.toFile());
        }

        serviceRepository.saveAndFlush(service);
    }
    @Override
    public boolean deleteServiceById(Integer id) {
        try {
            serviceRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return !serviceRepository.existsById(id);
    }
    @Override
    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
    @Override
    public List<ServiceWithCategoryDto> getPopularServices() throws NoSuchElementException {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<com.beautyzone.beautysalonapp.domain.Service> services = serviceRepository.findTopThreeServicesWithMostAppointmentsWithStartDate(thirtyDaysAgo)
                .stream()
                .limit(3) // Ensure we only get top 3 results
                .toList();;
        if (services.isEmpty()) {
            throw new NoSuchElementException("No service found!");
        }
        return serviceMapper.servicesToServiceWithCategoryDtos(services);
    }
}