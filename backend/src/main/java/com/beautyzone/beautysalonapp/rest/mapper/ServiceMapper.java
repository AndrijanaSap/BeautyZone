package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.Service;
import com.beautyzone.beautysalonapp.rest.dto.ServiceDto;
import com.beautyzone.beautysalonapp.rest.dto.ServiceWithCategoryDto;
import com.beautyzone.beautysalonapp.rest.dto.ServiceWithEmployeesDto;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    Service serviceDtoToService(ServiceDto serviceDto);
    @Named("serviceToServiceDto")

    ServiceDto serviceToServiceDto(Service service);

    ServiceWithCategoryDto serviceToServiceWithCategoryDto(Service service);
    @Named("serviceToServiceWithEmployeeesDto")
    ServiceWithEmployeesDto serviceToServiceWithEmployeeesDto(Service service);

    List<ServiceWithEmployeesDto> servicesToServiceWithEmployeeesDtos(List<Service> service);

    List<ServiceWithCategoryDto> servicesToServiceWithCategoryDtos(List<Service> services);
}
