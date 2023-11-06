package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.repository.ServiceRepository;
import com.beautyzone.beautysalonapp.rest.dto.ServiceDto;
import com.beautyzone.beautysalonapp.rest.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public List<ServiceDto> findAll() throws Exception {
        List<com.beautyzone.beautysalonapp.domain.Service> services = serviceRepository.findAll();
        if (services.isEmpty()) {
            throw new Exception("No service found!");
        }
        return serviceMapper.servicesToServiceDtos(services);
    }

}