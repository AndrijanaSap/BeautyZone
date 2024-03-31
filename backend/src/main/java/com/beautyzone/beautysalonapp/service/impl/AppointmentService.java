package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.AppointmentStatus;
import com.beautyzone.beautysalonapp.constants.PaymentMethod;
import com.beautyzone.beautysalonapp.constants.TimeSlotType;
import com.beautyzone.beautysalonapp.domain.Appointment;
//import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.Timeslot;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.repository.AppointmentRepository;
import com.beautyzone.beautysalonapp.repository.EmployeeRepository;
import com.beautyzone.beautysalonapp.repository.ServiceRepository;
import com.beautyzone.beautysalonapp.repository.TimeSlotRepository;
import com.beautyzone.beautysalonapp.rest.dto.*;
import com.beautyzone.beautysalonapp.rest.mapper.AppointmentMapper;
import com.beautyzone.beautysalonapp.rest.mapper.CategoryMapper;
import com.beautyzone.beautysalonapp.rest.mapper.TimeSlotMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Hex;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final CategoryMapper categoryMapper;
    private final AppointmentMapper appointmentMapper;
    private final int timeSlotUnitInMinutes = 30;

    public AppointmentResponseDto findById(Integer id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Appointment with id: " + id + "not found." ));
        return appointmentMapper.appointmentToAppointmentResponseDto(appointment);
    }

    public List<AppointmentResponseDto> findAllByClientId(Integer id) {
        List<Appointment> appointments = appointmentRepository.findAllByClient_Id(id);
        return appointmentMapper.appointmentsToAppointmentResponseDtos(appointments);
    }
    public AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto) {
        try {
            List<Timeslot> timeslots = timeSlotRepository.findAllById(appointmentRequestDto.getTimeSlotIds());

            Appointment appointment = new Appointment();
            appointment.setPaymentMethod(PaymentMethod.valueOf(appointmentRequestDto.getPaymentMethod()));
            appointment.setName(appointmentRequestDto.getName());
            appointment.setPhoneNumber(appointmentRequestDto.getPhoneNumber());
            appointment.setEmail(appointmentRequestDto.getEmail());
            appointment.setNote(appointmentRequestDto.getNote());
            appointment.setCreatedAt(LocalDateTime.now());
            appointment.setUpdatedAt(LocalDateTime.now());
            appointment.setService(serviceRepository.getReferenceById(appointmentRequestDto.getServiceId()));
            appointment.setEmployee(employeeRepository.getReferenceById(appointmentRequestDto.getEmployeeId()));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User client = ((User) authentication.getPrincipal());
            appointment.setClient(client);

            if (appointmentRequestDto.getPaymentMethod().equals(PaymentMethod.CASH.toString())) {
                appointment.setAppointmentStatus(AppointmentStatus.RESERVED);
            } else if(appointmentRequestDto.getPaymentMethod().equals(PaymentMethod.CARD.toString())){
                appointment.setAppointmentStatus(AppointmentStatus.WAITING_FOR_PAYMENT);
            } else  {
                throw new NoSuchElementException("Unknown payment method type");
            }

            Appointment appointmentDb = appointmentRepository.save(appointment);

            LocalDateTime earliestStartTime = timeslots.get(0).getStartTime();

            for (Timeslot timeslot : timeslots) {
                if (timeslot.getStartTime().isBefore(earliestStartTime)) {
                    earliestStartTime = timeslot.getStartTime();
                }
                timeslot.setAppointment(appointmentDb);
                timeslot.setTimeSlotType(TimeSlotType.SCHEDULED);
            }

            appointment.setAppointmentDateTime(earliestStartTime);
            appointment.setTimeslots(timeslots);
            appointmentDb = appointmentRepository.save(appointment);

            return appointmentMapper.appointmentToAppointmentResponseDto(appointmentDb);
        } catch (Exception e) {
            return null;
        }
    }


    public String calculateSignature(String secretKey, Map<String, String> params) throws NoSuchAlgorithmException, InvalidKeyException {
        String requestParamsStr = params.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(s -> s.getKey() + s.getValue()).collect(Collectors.joining());

        Gson gson = new Gson();

        return gson.toJson(calculateSignature(requestParamsStr, secretKey));
    }

    public String calculateSignature(String stringMessage, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        String calculatedSignature = "";

        SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        sha256HMAC.init(secret);

        calculatedSignature = Hex.encodeHexString(sha256HMAC.doFinal(stringMessage.getBytes(StandardCharsets.UTF_8)));


        return calculatedSignature;

    }

    public int handleSuccessfulPayment(IpgResponseDto ipgResponseDto) throws Exception {
        int appointmentId = Integer.parseInt(ipgResponseDto.getOrderNumber().replace("-test", ""));
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new NoSuchElementException("Appointment with id " + appointmentId + " has not be found: "));
        if (appointment.getAppointmentStatus().equals(AppointmentStatus.WAITING_FOR_PAYMENT)){
            appointment.setAppointmentStatus(AppointmentStatus.PAID);
            return appointmentId;
        }
        else throw new Exception("Expected appointment status is " + AppointmentStatus.WAITING_FOR_PAYMENT + " ,but we got " + appointment.getAppointmentStatus() + " ");
    }

    public int handleCanceledPayment(IpgResponseDto ipgResponseDto) throws Exception {
        int appointmentId = Integer.parseInt(ipgResponseDto.getOrderNumber().replace("-test", ""));
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new NoSuchElementException("Appointment with id " + appointmentId + " has not be found: "));
        if (appointment.getAppointmentStatus().equals(AppointmentStatus.WAITING_FOR_PAYMENT)){
            appointment.setAppointmentStatus(AppointmentStatus.PAID);
            return appointmentId;
        }
        else throw new Exception("Expected appointment status is " + AppointmentStatus.WAITING_FOR_PAYMENT + " ,but we got " + appointment.getAppointmentStatus() + " ");
    }
}