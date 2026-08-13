package com.autocare.autocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.ServiceBooking;

public interface ServiceBookingRepository
        extends JpaRepository<ServiceBooking, Long> {

}