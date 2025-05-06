package com.psy.services.payment;

import java.time.LocalDateTime;

public record PaymentHistoryResponse(
        Integer id,
        LocalDateTime paymentDate,
        LocalDateTime appointmentDate,
        String doctorName,
        String appointmentType
) {
}
