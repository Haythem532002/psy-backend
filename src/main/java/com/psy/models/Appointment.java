package com.psy.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Appointment {
    @Id
    @GeneratedValue
    Integer id;
    @ManyToOne
    private User user;

    LocalDateTime date;
    @Enumerated(EnumType.STRING)
    AppointmentType type;

    @ManyToOne
    Doctor doctor;

}