package com.psy.services.payment;

import com.psy.controllers.appointment.AppointmentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentResponse createPaymentLink(AppointmentRequest appointmentRequest) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        log.info(String.valueOf("price"+appointmentRequest.getPrice()));
        log.info(String.valueOf("type"+appointmentRequest.getAppointmentType()));
        log.info(String.valueOf("doctor id"+appointmentRequest.getDoctorId()));
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .putMetadata("userId", String.valueOf(appointmentRequest.getUserId()))
                .putMetadata("doctorId", String.valueOf(appointmentRequest.getDoctorId()))
                .putMetadata("type", appointmentRequest.getAppointmentType())
                .putMetadata("date", appointmentRequest.getAppointmentDateTime())
                .setSuccessUrl("http://localhost:4200/payment/success")
                .setCancelUrl("http://localhost:4200/payment/fail")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount((long) appointmentRequest.getPrice()) // e.g. 5000 = $50.00
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Psychiatric Consultation")
                                                                .build())
                                                .build()
                                )
                                .setQuantity(1L)
                                .build())
                .build();

        Session session = Session.create(params);
        return PaymentResponse.builder()
                .payment_url(session.getUrl())
                .build();
    }
}
