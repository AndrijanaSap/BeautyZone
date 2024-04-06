package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.Appointment;
import com.beautyzone.beautysalonapp.domain.Category;
import com.beautyzone.beautysalonapp.rest.dto.AppointmentCreationResponseDto;
import com.beautyzone.beautysalonapp.rest.dto.AppointmentWithClientResponseDto;
import com.beautyzone.beautysalonapp.rest.dto.AppointmentWithEmployeeResponseDto;
import com.beautyzone.beautysalonapp.rest.dto.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentWithEmployeeResponseDto appointmentToAppointmentWithEmployeeResponseDto(Appointment appointment);

    List<AppointmentWithEmployeeResponseDto> appointmentsToAppointmentWithEmployeeResponseDtos(List<Appointment> appointments);

    AppointmentWithClientResponseDto appointmentToAppointmentWithClientResponseDto(Appointment appointment);

    List<AppointmentWithClientResponseDto> appointmentsToAppointmentWithClientResponseDtos(List<Appointment> appointments);

    AppointmentCreationResponseDto appointmentToAppointmentCreationResponseDto(Appointment appointment);
}
