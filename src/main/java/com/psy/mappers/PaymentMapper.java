package com.psy.mappers;

import com.psy.models.Appointment;
import com.psy.services.payment.PaymentHistoryResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentHistoryResponse toPaymentHistoryResponse(
            Appointment appointment
    ) {
        var doctorName = appointment.getDoctor().getFirstname() + " " + appointment.getDoctor().getLastname();
        var appointmentDate = appointment.getDate();
        var paymentDate = appointment.getPayment().getDateTime();
        var appointmentType = appointment.getType().name();
        int price = appointment.getDoctor().getPrice();
        return new PaymentHistoryResponse(
                appointment.getPayment().getId(),
                paymentDate,
                appointmentDate,
                doctorName,
                appointmentType,
                price
        );
    }
}
