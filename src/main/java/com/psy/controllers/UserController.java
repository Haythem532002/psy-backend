package com.psy.controllers;

import com.psy.dtos.appointmentDto.AppointmentDto;
import com.psy.models.Appointment;
import com.psy.services.UserAuthService;
import com.psy.services.UserService;
import com.psy.services.payment.PaymentHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserAuthService userAuthService;
    private final UserService userService;

    @GetMapping("/email")
    public ResponseEntity<Integer> getUserIdByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userAuthService.getUserIdByEmail(email));
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<List<AppointmentDto>> getAvailableAppointments(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(userService.getAvailableAppointments(id));
    }

    @GetMapping("/payment-history/{id}")
    public ResponseEntity<List<PaymentHistoryResponse>> getPaymentHistory(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(userService.getPaymentHistory(id));
    }
}
