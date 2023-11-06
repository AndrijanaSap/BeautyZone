package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.Service;
import com.beautyzone.beautysalonapp.rest.dto.ServiceDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    Service serviceDtoToService(ServiceDto serviceDto);
    ServiceDto serviceToServiceDto(Service service);
    List<ServiceDto> servicesToServiceDtos(List<Service> services);
}
