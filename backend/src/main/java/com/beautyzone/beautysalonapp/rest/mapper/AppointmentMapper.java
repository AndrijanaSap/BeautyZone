package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.Appointment;
import com.beautyzone.beautysalonapp.domain.Category;
import com.beautyzone.beautysalonapp.rest.dto.AppointmentCreationResponseDto;
import com.beautyzone.beautysalonapp.rest.dto.AppointmentResponseDto;
import com.beautyzone.beautysalonapp.rest.dto.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentResponseDto appointmentToAppointmentResponseDto(Appointment appointment);

    List<AppointmentResponseDto> appointmentsToAppointmentResponseDtos(List<Appointment> appointments);

    AppointmentCreationResponseDto appointmentToAppointmentCreationResponseDto(Appointment appointment);
}
