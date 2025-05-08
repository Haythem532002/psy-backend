package com.psy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class Doctor {

    @Id
    @GeneratedValue
    Integer id;

    String firstname;
    String lastname;
    String phone;
    String email;
    String address;
    String city;
    String gender;
    String education;
    int experienceYears;
    int price;
    String specialization;
    String description;
    int rating = 0;
}
