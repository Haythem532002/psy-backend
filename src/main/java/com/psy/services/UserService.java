package com.psy.services;

import com.psy.dtos.appointmentDto.AppointmentDto;
import com.psy.mappers.AppointmentMapper;
import com.psy.mappers.PaymentMapper;
import com.psy.models.User;
import com.psy.repositories.UserRepository;
import com.psy.services.payment.PaymentHistoryResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final AppointmentMapper appointmentMapper;
    private final PaymentMapper paymentMapper;

    public User createOrUpdateUser(User user) {
        return repository.save(user);
    }


    public List<User> getAllUser() {
        return repository.findAll();
    }

    public User getUser(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
    }

    public void deleteUser(Integer id) {
        repository.deleteById(id);
    }

    public List<AppointmentDto> getAvailableAppointments(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"))
                .getAppointments()
                .stream()
                .map(appointmentMapper::toDto)
                .toList()
                ;
    }

    public List<PaymentHistoryResponse> getPaymentHistory(Integer userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"))
                .getAppointments()
                .stream()
                .filter(appointment -> appointment.getPayment() != null)
                .toList()
                .stream()
                .map(paymentMapper::toPaymentHistoryResponse)
                .toList();
    }

}
