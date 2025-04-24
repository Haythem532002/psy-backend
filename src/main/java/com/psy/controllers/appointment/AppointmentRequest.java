package com.psy.controllers.appointment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
    Integer userId;
    Integer doctorId;
    String appointmentType;
    String appointmentDateTime;
    Integer price;
}
