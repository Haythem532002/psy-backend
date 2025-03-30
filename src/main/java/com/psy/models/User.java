package com.psy.models;

import com.psy.user.UserAuth;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_user")
@Entity
public class User extends UserAuth {

    @OneToMany(mappedBy = "user")
    List<Appointment> appointments;

}
