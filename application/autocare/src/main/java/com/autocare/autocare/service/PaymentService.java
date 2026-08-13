package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.entity.Payment;
import com.autocare.autocare.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(
            PaymentRepository paymentRepository) {

        this.paymentRepository = paymentRepository;
    }

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get one payment by ID
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    // Add or update payment
    public Payment savePayment(Payment payment) {

        if (payment.getPaymentId() != null) {

            Payment existingPayment =
                    paymentRepository.findById(
                            payment.getPaymentId()
                    ).orElse(null);

            if (existingPayment != null) {

                // Keep original payment date
                payment.setPaymentDate(
                        existingPayment.getPaymentDate()
                );
            }

        } else {

            // New payment
            payment.setPaymentDate(LocalDateTime.now());

            if (payment.getPaymentStatus() == null
                    || payment.getPaymentStatus().isBlank()) {

                payment.setPaymentStatus("Completed");
            }
        }

        return paymentRepository.save(payment);
    }

    // Delete payment
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}