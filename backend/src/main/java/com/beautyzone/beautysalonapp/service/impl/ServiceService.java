package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.repository.ServiceRepository;
import com.beautyzone.beautysalonapp.rest.dto.ServiceWithCategoryDto;
import com.beautyzone.beautysalonapp.rest.mapper.ServiceMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public List<ServiceWithCategoryDto> findAll() throws NoSuchElementException {
        List<com.beautyzone.beautysalonapp.domain.Service> services = serviceRepository.findAll();
        if (services.isEmpty()) {
            throw new NoSuchElementException("No service found!");
        }
        return serviceMapper.servicesToServiceWithCategoryDtos(services);
    }

    public ServiceWithCategoryDto findById(Integer id) {
        com.beautyzone.beautysalonapp.domain.Service service = serviceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Service not found with id: " + id));
        return serviceMapper.serviceToServiceWithCategoryDto(service);
    }

}