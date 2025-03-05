package com.psy.auth;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateRequest {
    @Column(length = 1024)
    String token;
}
