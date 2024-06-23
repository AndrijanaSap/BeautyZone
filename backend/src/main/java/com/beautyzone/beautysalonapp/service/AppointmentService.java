package com.beautyzone.beautysalonapp.service;

import com.beautyzone.beautysalonapp.rest.dto.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public interface AppointmentService {
    AppointmentWithEmployeeResponseDto findById(Integer id);

    List<AppointmentWithEmployeeResponseDto> findAll();

    List<AppointmentWithEmployeeResponseDto> findAllByClientId(Integer id);

    List<AppointmentWithClientResponseDto> findAllByEmployeeId(Integer id);

    AppointmentWithEmployeeResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto);

    boolean updateAppointment(AppointmentRequestDto appointmentRequestDto);

    String calculateSignature(String secretKey, Map<String, String> params) throws NoSuchAlgorithmException, InvalidKeyException;

    String calculateSignature(String stringMessage, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException;

    int handleSuccessfulPayment(IpgResponseDto ipgResponseDto) throws Exception;

    int handleCanceledPayment(IpgResponseDto ipgResponseDto) throws Exception;

    void updateAppointmentCustomerData(AppointmentCustomerDataUpdateRequestDto updateRequestDto);

    boolean deleteAppointmentById(Integer id);
}