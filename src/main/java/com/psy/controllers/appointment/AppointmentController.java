package com.psy.controllers.appointment;

import com.psy.models.Appointment;
import com.psy.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService service;

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(service.getAppointment(id));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(service.getAllAppointments());
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody Appointment appointment
    ) {
        return ResponseEntity.ok(service.createOrUpdateAppointment(appointment));
    }

    @PutMapping
    public ResponseEntity<Appointment> updateAppointment(
            @RequestBody Appointment appointment
    ) {
        return ResponseEntity.ok(service.createOrUpdateAppointment(appointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable("id") Integer id
    ) {
        service.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }

}
