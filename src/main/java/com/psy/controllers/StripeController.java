package com.psy.controllers;

import com.psy.Utils.DateParser;
import com.psy.models.Appointment;
import com.psy.models.AppointmentType;
import com.psy.models.Payment;
import com.psy.services.AppointmentService;
import com.psy.services.DoctorService;
import com.psy.services.UserService;
import com.psy.services.payment.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
@RequiredArgsConstructor
public class StripeController {

    private static final String STRIPE_SECRET_KEY = "whsec_dd93f1a7e4f028acaa40f35c15cd901861d5848bc5f1324117a24570a411ec7f";
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final UserService userService;
    private final PaymentService paymentService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) throws IOException {
        String payload = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String sigHeader = request.getHeader("Stripe-Signature");

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, STRIPE_SECRET_KEY);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getData().getObject();

            try {
                Map<String, String> metadata = session.getMetadata();
                String userIdStr = metadata.get("userId");
                String doctorIdStr = metadata.get("doctorId");
                String typeStr = metadata.get("type");
                String dateStr = metadata.get("date");

                if (userIdStr == null || doctorIdStr == null || typeStr == null || dateStr == null) {
                    System.err.println("Missing metadata fields. Cannot create appointment.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing metadata fields");
                }

                Integer userId = Integer.valueOf(userIdStr);
                Integer doctorId = Integer.valueOf(doctorIdStr);
                AppointmentType type = AppointmentType.valueOf(typeStr.toUpperCase());
                LocalDateTime date = DateParser.parseDateString(dateStr);

                var user = userService.getUser(userId);
                var doctor = doctorService.getDoctorById(doctorId);

                var payment = paymentService.createOrUpdatePayment(
                        Payment.builder()
                                .dateTime(LocalDateTime.now())
                                .build()
                );

                Appointment appointment = Appointment.builder()
                        .user(user)
                        .doctor(doctor)
                        .type(type)
                        .date(date)
                        .payment(payment)
                        .build();

                appointmentService.createOrUpdateAppointment(appointment);
            } catch (Exception e) {
                System.err.println("Error while creating appointment: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment data");
            }
        }
        return ResponseEntity.ok("Webhook processed successfully");
    }
}
