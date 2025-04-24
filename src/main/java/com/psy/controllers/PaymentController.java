package com.psy.controllers;

import com.psy.controllers.appointment.AppointmentRequest;
import com.psy.services.payment.PaymentResponse;
import com.psy.services.payment.StripeService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final StripeService stripeService;


    @PostMapping("/create-checkout")
    public PaymentResponse createCheckoutSession(@RequestBody AppointmentRequest appointmentRequest) throws StripeException {
        return stripeService.createPaymentLink(appointmentRequest);
    }
}

