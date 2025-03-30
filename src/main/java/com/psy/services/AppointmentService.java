package com.psy.services;

import com.psy.models.Appointment;
import com.psy.repositories.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository repository;

    public Appointment getAppointment(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Appointment Not Found")
        );
    }

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Appointment createOrUpdateAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    public void deleteAppointment(Integer id) {
        repository.deleteById(id);
    }
}
