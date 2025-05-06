package com.psy.models;

import com.psy.user.UserAuth;
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
    User user;

    LocalDateTime date;
    @Enumerated(EnumType.STRING)
    AppointmentType type;

    @ManyToOne
    Doctor doctor;

    @OneToOne
    Payment payment;

}