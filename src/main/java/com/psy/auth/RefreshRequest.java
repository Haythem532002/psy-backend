package com.psy.auth;

import jakarta.persistence.Column;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class RefreshRequest {
    @Column(length = 1024)
    String refreshToken;
}
