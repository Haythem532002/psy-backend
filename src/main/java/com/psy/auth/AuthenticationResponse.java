package com.psy.auth;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    @Column(length = 1024)
    String accessToken;
    @Column(length = 1024)
    String refreshToken;
}
