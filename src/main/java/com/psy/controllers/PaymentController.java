package com.psy.controllers;

import com.psy.services.payment.PaymentResponse;
import com.psy.services.payment.StripeService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final StripeService stripeService;


    @GetMapping("/create-checkout")
    public PaymentResponse createCheckoutSession() throws StripeException {
        long consultationPrice = 5000L; // $50.00 (in cents)
        return stripeService.createPaymentLink(consultationPrice);
    }
}

