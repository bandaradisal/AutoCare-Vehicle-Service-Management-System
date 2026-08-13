package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.entity.ServiceBooking;
import com.autocare.autocare.repository.ServiceBookingRepository;

@Service
public class ServiceBookingService {

    private final ServiceBookingRepository serviceBookingRepository;

    public ServiceBookingService(
            ServiceBookingRepository serviceBookingRepository) {

        this.serviceBookingRepository = serviceBookingRepository;
    }

    // Get all service bookings
    public List<ServiceBooking> getAllBookings() {
        return serviceBookingRepository.findAll();
    }

    // Get one booking by ID
    public ServiceBooking getBookingById(Long id) {
        return serviceBookingRepository.findById(id).orElse(null);
    }

    // Add or update booking
    public ServiceBooking saveBooking(ServiceBooking booking) {

        if (booking.getBookingId() != null) {

            ServiceBooking existingBooking =
                    serviceBookingRepository.findById(booking.getBookingId())
                            .orElse(null);

            if (existingBooking != null) {

                booking.setBookingDate(
                        existingBooking.getBookingDate()
                );
            }

        } else {

            // New booking
            booking.setBookingDate(LocalDateTime.now());

            if (booking.getBookingStatus() == null
                    || booking.getBookingStatus().isBlank()) {

                booking.setBookingStatus("Pending");
            }
        }

        return serviceBookingRepository.save(booking);
    }

    // Delete booking
    public void deleteBooking(Long id) {
        serviceBookingRepository.deleteById(id);
    }
}