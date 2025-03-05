package com.psy.token;

import com.psy.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue
    Integer id;
    @Column(length = 1024)
    String token;
    @Column(length = 1024)
    String refreshToken;
    @Enumerated(EnumType.STRING)
    TokenType tokenType;

    @ManyToOne
    User user;
}
