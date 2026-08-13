package com.autocare.autocare.mongo_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.autocare.autocare.document.ServiceHistory;

public interface ServiceHistoryRepository
        extends MongoRepository<ServiceHistory, String> {

    // Find one service history record by custom history ID
    Optional<ServiceHistory> findByHistoryId(String historyId);

    // Find all service history records for one vehicle
    List<ServiceHistory> findByVehicleId(Long vehicleId);

    // Find all service history records for one booking
    List<ServiceHistory> findByBookingId(Long bookingId);

    // Find all service history records by service type
    List<ServiceHistory> findByServiceType(String serviceType);
}