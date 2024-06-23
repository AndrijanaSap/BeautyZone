package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.AppointmentStatus;
import com.beautyzone.beautysalonapp.constants.PaymentMethod;
import com.beautyzone.beautysalonapp.constants.TimeSlotType;
import com.beautyzone.beautysalonapp.domain.Appointment;
//import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.Timeslot;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.repository.*;
import com.beautyzone.beautysalonapp.rest.dto.*;
import com.beautyzone.beautysalonapp.rest.mapper.AppointmentMapper;
import com.beautyzone.beautysalonapp.service.AppointmentService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Hex;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    public static final String RESPONSE_CODE_ELEMENT = "response-code";
    public static final String DESCRIPTION_ELEMENT = "description";

    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentMapper appointmentMapper;
    private final int timeSlotUnitInMinutes = 30;

    @Override
    public AppointmentWithEmployeeResponseDto findById(Integer id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Appointment with id: " + id + "not found."));
        return appointmentMapper.appointmentToAppointmentWithEmployeeResponseDto(appointment);
    }

    @Override
    public List<AppointmentWithEmployeeResponseDto> findAll() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointmentMapper.appointmentsToAppointmentWithEmployeeResponseDtos(appointments);
    }

    @Override
    public List<AppointmentWithEmployeeResponseDto> findAllByClientId(Integer id) {
        List<Appointment> appointments = appointmentRepository.findAllByClient_Id(id);
        return appointmentMapper.appointmentsToAppointmentWithEmployeeResponseDtos(appointments);
    }

    @Override
    public List<AppointmentWithClientResponseDto> findAllByEmployeeId(Integer id) {
        List<Appointment> appointments = appointmentRepository.findAllByEmployee_Id(id);
        return appointmentMapper.appointmentsToAppointmentWithClientResponseDtos(appointments);
    }

    @Override
    public AppointmentWithEmployeeResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto) {
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
            if (!authentication.getPrincipal().equals("anonymousUser")) {
                User client = ((User) authentication.getPrincipal());
                appointment.setClient(client);
            }

            if (appointmentRequestDto.getPaymentMethod().equals(PaymentMethod.CASH.toString())) {
                appointment.setAppointmentStatus(AppointmentStatus.RESERVED);
            } else if (appointmentRequestDto.getPaymentMethod().equals(PaymentMethod.CARD.toString())) {
                appointment.setAppointmentStatus(AppointmentStatus.WAITING_FOR_PAYMENT);
            } else {
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

            return appointmentMapper.appointmentToAppointmentWithEmployeeResponseDto(appointmentDb);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateAppointment(AppointmentRequestDto appointmentRequestDto) {
        try {
            Appointment appointment = appointmentRepository.findById(Integer.valueOf(appointmentRequestDto.getId()))
                    .orElseThrow(() -> new UsernameNotFoundException("Appointment not found"));
            List<Timeslot> newTimeslots = timeSlotRepository.findAllById(appointmentRequestDto.getTimeSlotIds());
            List<Timeslot> oldTimeslots = timeSlotRepository.findAllById(
                    appointment.getTimeslots().stream().map(Timeslot::getId).collect(Collectors.toList())
            );

            // Update the modified fields
            if (!appointment.getName().equals(appointmentRequestDto.getName()))
                appointment.setName(appointmentRequestDto.getName());
            if (!appointment.getPhoneNumber().equals(appointmentRequestDto.getPhoneNumber()))
                appointment.setPhoneNumber(appointmentRequestDto.getPhoneNumber());
            if (!appointment.getEmail().equals(appointmentRequestDto.getEmail()))
                appointment.setEmail(appointmentRequestDto.getEmail());
            if (!appointment.getNote().equals(appointmentRequestDto.getNote()))
                appointment.setNote(appointmentRequestDto.getNote());
            appointment.setUpdatedAt(LocalDateTime.now());

            if (!appointment.getService().getId().equals(appointmentRequestDto.getServiceId()))
                appointment.setService(serviceRepository.getReferenceById(appointmentRequestDto.getServiceId()));

            if (!appointment.getEmployee().getId().equals(appointmentRequestDto.getEmployeeId()))
                appointment.setEmployee(employeeRepository.getReferenceById(appointmentRequestDto.getEmployeeId()));

            // Update the timeslots if they are modified
            if (!oldTimeslots.equals(newTimeslots)) {
                // Update the new timeslots with the new appointment, update the timeslot status and get the startTime for the appointment
                LocalDateTime startTime = newTimeslots.get(0).getStartTime();

                for (Timeslot timeslot : newTimeslots) {
                    if (timeslot.getStartTime().isBefore(startTime)) {
                        startTime = timeslot.getStartTime();
                    }
                    timeslot.setAppointment(appointment);
                    timeslot.setTimeSlotType(TimeSlotType.SCHEDULED);
                }

                appointment.setAppointmentDateTime(startTime);
                appointment.setTimeslots(newTimeslots);

                // Make old timeslots available again
                for (Timeslot timeslot : oldTimeslots) {
                    timeslot.setAppointment(null);
                    timeslot.setTimeSlotType(TimeSlotType.AVAILABLE);
                }
            }

            appointmentRepository.save(appointment);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String calculateSignature(String secretKey, Map<String, String> params) throws NoSuchAlgorithmException, InvalidKeyException {
        String requestParamsStr = params.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(s -> s.getKey() + s.getValue()).collect(Collectors.joining());

        Gson gson = new Gson();

        return gson.toJson(calculateSignature(requestParamsStr, secretKey));
    }

    @Override
    public String calculateSignature(String stringMessage, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        String calculatedSignature = "";

        SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        sha256HMAC.init(secret);

        calculatedSignature = Hex.encodeHexString(sha256HMAC.doFinal(stringMessage.getBytes(StandardCharsets.UTF_8)));


        return calculatedSignature;

    }

    @Override
    public int handleSuccessfulPayment(IpgResponseDto ipgResponseDto) throws Exception {
        int appointmentId = Integer.parseInt(ipgResponseDto.getOrderNumber().replace("-test", ""));
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new NoSuchElementException("Appointment with id " + appointmentId + " has not be found: "));
        if (appointment.getAppointmentStatus().equals(AppointmentStatus.WAITING_FOR_PAYMENT) || appointment.getAppointmentStatus().equals(AppointmentStatus.RESERVED)) {
            appointment.setAppointmentStatus(AppointmentStatus.PAID);
            appointment.setPaymentMethod(PaymentMethod.CARD);
            appointmentRepository.save(appointment);
            return appointmentId;
        } else
            throw new Exception("Expected appointment statuses are " + AppointmentStatus.WAITING_FOR_PAYMENT + " , or " + AppointmentStatus.RESERVED + " but we got " + appointment.getAppointmentStatus() + " ");
    }

    @Override
    public int handleCanceledPayment(IpgResponseDto ipgResponseDto) throws Exception {
        int appointmentId = Integer.parseInt(ipgResponseDto.getOrderNumber().replace("-test", ""));
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new NoSuchElementException("Appointment with id " + appointmentId + " has not been found: "));
        if (appointment.getAppointmentStatus().equals(AppointmentStatus.WAITING_FOR_PAYMENT)) {
            deleteAppointmentById(appointmentId);
            return appointmentId;
        } else if (appointment.getAppointmentStatus().equals(AppointmentStatus.RESERVED)) {
            return appointmentId;
        } else
            throw new Exception("Expected appointment statuses are " + AppointmentStatus.WAITING_FOR_PAYMENT + " , or " + AppointmentStatus.RESERVED + " but we got " + appointment.getAppointmentStatus() + " ");
    }

    @Override
    public void updateAppointmentCustomerData(AppointmentCustomerDataUpdateRequestDto updateRequestDto) {
        Appointment appointment = appointmentRepository.findById(updateRequestDto.getId()).orElseThrow(() -> new NoSuchElementException("Appointment not found with id: " + updateRequestDto.getId()));
        appointment.setName(updateRequestDto.getName());
        appointment.setEmail(updateRequestDto.getEmail());
        appointment.setNote(updateRequestDto.getNote());
        appointment.setPhoneNumber(updateRequestDto.getPhone());

        appointmentRepository.save(appointment);
    }

    @Override
    public boolean deleteAppointmentById(Integer id) {
        try {
            Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Appointment not found"));
            //Refund if the client already paid
            if (appointment.getAppointmentStatus().equals(AppointmentStatus.PAID)) {
                try {
                    char[] password = "Aneronaldo7!".toCharArray();
                    KeyStore keyStore = KeyStore.getInstance("PKCS12");
                    FileInputStream fis = new FileInputStream("C:\\Users\\andrijana.saplamaeva\\Documents\\cert\\CorvusPay.p12");
                    keyStore.load(fis, password);

                    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    keyManagerFactory.init(keyStore, password);

                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init((KeyStore) null);

                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

                    URL url = new URL("https://testcps.corvus.hr/refund");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    if (connection instanceof HttpsURLConnection) {
                        ((HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
                    }
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                    dataOutputStream.writeBytes(getParametersForRefund(13660,appointment.getId() + "-test", "c1L9yX1yrEEkQVGu2fBz5hImz"));
                    dataOutputStream.flush();
                    dataOutputStream.close();

                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code: " + responseCode);

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    if (!(response.toString().isEmpty())) {
                        Element root = DocumentBuilderFactory
                                .newInstance()
                                .newDocumentBuilder()
                                .parse(new ByteArrayInputStream(response.toString().getBytes("UTF-8")))
                                .getDocumentElement();
                        NodeList orderResponseCodesAsList = root.getElementsByTagName(RESPONSE_CODE_ELEMENT);
                        Node orderResponseCodeAsNode = getResponseCodeAsNode(orderResponseCodesAsList, root);
                        NodeList errorDescriptionAsList = root.getElementsByTagName(DESCRIPTION_ELEMENT);
                        if (orderResponseCodeAsNode != null) {
                            if(!Integer.valueOf(orderResponseCodeAsNode.getTextContent()).equals(0))
                                return false;
                        }
                        if (errorDescriptionAsList != null && errorDescriptionAsList.getLength() > 0) {
                            return false;
                        }
                    } else {
                        throw new IllegalStateException("Empty response received.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            appointment.getTimeslots().forEach(timeslot -> {
                timeslot.setTimeSlotType(TimeSlotType.AVAILABLE);
                timeslot.setAppointment(null);
            });

            appointmentRepository.save(appointment);

            appointmentRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return !appointmentRepository.existsById(id);
    }

    private String getParametersForRefund(Integer storeId, String orderNumber, String key) throws UnsupportedEncodingException {
        return "store_id=" + storeId +
                "&order_number=" + URLEncoder.encode(orderNumber, "UTF-8") +
                "&hash=" + DigestUtils.sha1Hex(key + orderNumber + storeId);
    }

    private Node getResponseCodeAsNode(NodeList responseCodeNodeList, Element root){
        for (int i = 0; i<responseCodeNodeList.getLength();i++) {
            if(responseCodeNodeList.item(i).getParentNode().isSameNode(root))
                return responseCodeNodeList.item(i);
        }
        return null;
    }

}