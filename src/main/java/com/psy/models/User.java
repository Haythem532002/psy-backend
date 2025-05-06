package com.psy.models;

import com.psy.user.UserAuth;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "_user")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends UserAuth {

    @OneToMany(mappedBy = "user")
    List<Appointment> appointments;

}
