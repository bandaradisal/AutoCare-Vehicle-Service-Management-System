package com.autocare.autocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.Payment;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

}