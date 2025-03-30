package com.psy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    Doctor doctor;

}