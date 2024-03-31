package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.Service;
import com.beautyzone.beautysalonapp.rest.dto.ServiceDto;
import com.beautyzone.beautysalonapp.rest.dto.ServiceWithCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    Service serviceDtoToService(ServiceDto serviceDto);

    ServiceDto serviceToServiceDto(Service service);

    ServiceWithCategoryDto serviceToServiceWithCategoryDto(Service service);

    List<ServiceWithCategoryDto> servicesToServiceWithCategoryDtos(List<Service> services);
}
