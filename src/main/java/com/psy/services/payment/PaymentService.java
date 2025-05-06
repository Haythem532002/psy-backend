package com.psy.services.payment;

import com.psy.models.Payment;
import com.psy.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;

    public Payment createOrUpdatePayment(Payment user) {
        return repository.save(user);
    }

    public List<Payment> getAllPayment() {
        return repository.findAll();
    }

    public Payment getPayment(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment Not Found"));
    }

    public void deletePayment(Integer id) {
        repository.deleteById(id);
    }

}
