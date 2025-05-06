package com.psy.dtos.appointmentDto;

import com.psy.models.Doctor;

import java.time.LocalDateTime;

public record AppointmentDto(
        Integer id,
        LocalDateTime dateTime,
        Doctor doctor
) {
}
