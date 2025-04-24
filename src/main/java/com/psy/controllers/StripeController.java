package com.psy.controllers;

import com.psy.models.Appointment;
import com.psy.models.AppointmentType;
import com.psy.services.AppointmentService;
import com.psy.services.DoctorService;
import com.psy.user.UserAuthService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/stripe")
@RequiredArgsConstructor
public class StripeController {

    private final AppointmentService appointmentService;

    private final DoctorService doctorService;
    private final UserAuthService userAuthService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) throws IOException {
        String payload = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String sigHeader = request.getHeader("Stripe-Signature");

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, "whsec_dd93f1a7e4f028acaa40f35c15cd901861d5848bc5f1324117a24570a411ec7f");
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Optional<StripeObject> stripeObject = event.getDataObjectDeserializer().getObject();

            if (stripeObject.isPresent()) {
                Session session = (Session) stripeObject.get();

                Integer userId = Integer.valueOf(session.getMetadata().get("userId"));
                Integer doctorId = Integer.valueOf(session.getMetadata().get("doctorId"));
                AppointmentType type = AppointmentType.valueOf(session.getMetadata().get("type"));
                LocalDateTime date = LocalDateTime.parse(session.getMetadata().get("date"));

                var user = userAuthService.getUserById(userId);
                var doctor = doctorService.getDoctorById(doctorId);

                var appointment = Appointment.builder()
                        .user(user)
                        .doctor(doctor)
                        .type(type)
                        .date(date)
                        .build();

                appointmentService.createOrUpdateAppointment(appointment);
            } else {
                System.err.println("❌ Failed to deserialize Stripe session object for event: " + event.getId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid session object");
            }
        }


        return ResponseEntity.ok("");
    }
}