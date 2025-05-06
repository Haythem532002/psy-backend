package com.psy.mappers;

import com.psy.dtos.appointmentDto.AppointmentDto;
import com.psy.models.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentDto toDto(Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getDate(),
                appointment.getDoctor()
        );
    }
}
