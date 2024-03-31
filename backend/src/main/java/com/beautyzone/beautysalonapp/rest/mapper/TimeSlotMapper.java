package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.Appointment;
import com.beautyzone.beautysalonapp.domain.Service;
import com.beautyzone.beautysalonapp.rest.dto.AppointmentResponseDto;
import com.beautyzone.beautysalonapp.rest.dto.ServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
//    Appointment AppointmentResponseDtoToAppointment(AppointmentResponseDto appointmentResponseDto);
//    @Mapping(target = "category", source = "category", ignore = true) // Ignore the field 'category'
    AppointmentResponseDto appointmentToAppointmentResponseDto(Appointment appointment);
//    List<ServiceDto> servicesToServiceDtos(List<Service> services);
}
