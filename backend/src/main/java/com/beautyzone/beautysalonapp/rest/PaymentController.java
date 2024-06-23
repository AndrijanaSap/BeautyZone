package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.rest.dto.IpgResponseDto;
import com.beautyzone.beautysalonapp.service.impl.AppointmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private static final String SUCCESS_VIEW = "http://localhost:4200/thank-you";
    private static final String CANCEL_VIEW = "http://localhost:4200/services";

    private static final String ERROR_VIEW = "http://localhost:4200/error";

    private final AppointmentServiceImpl appointmentServiceImpl;

    @PostMapping("/success")
    public ModelAndView handleSuccess(
            @RequestParam String order_number,
            @RequestParam String language,
            @RequestParam String approval_code,
            @RequestParam String signature,
            @RequestParam(required = false) Integer account_id,
            @RequestParam(required = false) String subscription_exp_date,
            @RequestParam(required = false) Boolean discount_used
    ) {
        IpgResponseDto ipgResponseDto = new IpgResponseDto(order_number, language, approval_code, signature,
                account_id, subscription_exp_date, discount_used);
        try {
            int appointmentId = appointmentServiceImpl.handleSuccessfulPayment(ipgResponseDto);
            return new ModelAndView("redirect:" + SUCCESS_VIEW + "/" + appointmentId);
        } catch (Exception e) {
            return new ModelAndView("redirect:" + ERROR_VIEW);
        }
    }

    @PostMapping("/cancel")
    public ModelAndView handleCancel(@RequestParam String order_number, @RequestParam String language) {
        IpgResponseDto ipgResponseDto = new IpgResponseDto();
        ipgResponseDto.setOrderNumber(order_number);
        ipgResponseDto.setLanguage(language);
        try {
            int appointmentId = appointmentServiceImpl.handleCanceledPayment(ipgResponseDto);
            return new ModelAndView("redirect:" + CANCEL_VIEW + "/" + appointmentId);
        } catch (Exception e) {
            return new ModelAndView("redirect:" + ERROR_VIEW);
        }
    }
}