package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.document.ServiceHistory;
import com.autocare.autocare.mongo_repository.ServiceHistoryRepository;

@Service
public class ServiceHistoryService {

    private final ServiceHistoryRepository serviceHistoryRepository;

    public ServiceHistoryService(
            ServiceHistoryRepository serviceHistoryRepository) {

        this.serviceHistoryRepository = serviceHistoryRepository;
    }

    // Get all service history records
    public List<ServiceHistory> getAllServiceHistory() {
        return serviceHistoryRepository.findAll();
    }

    // Get one record using MongoDB internal ID
    public ServiceHistory getServiceHistoryById(String id) {
        return serviceHistoryRepository.findById(id)
                .orElse(null);
    }

    // Get one record using custom History ID
    // Example: SH001
    public ServiceHistory getServiceHistoryByHistoryId(
            String historyId) {

        return serviceHistoryRepository
                .findByHistoryId(historyId)
                .orElse(null);
    }

    // Save new or edited service history
    public ServiceHistory saveServiceHistory(
            ServiceHistory serviceHistory) {

        ServiceHistory existingHistory =
                serviceHistoryRepository
                        .findByHistoryId(
                                serviceHistory.getHistoryId()
                        )
                        .orElse(null);

        /*
         * EDIT EXISTING SERVICE HISTORY
         */
        if (existingHistory != null) {

            // Keep MongoDB internal _id
            serviceHistory.setId(
                    existingHistory.getId()
            );

            /*
             * Keep original service date if
             * no new date was supplied.
             */
            if (serviceHistory.getServiceDate() == null) {

                serviceHistory.setServiceDate(
                        existingHistory.getServiceDate()
                );
            }

            /*
             * Preserve parts_used because our
             * first HTML form will not edit parts.
             */
            if (serviceHistory.getPartsUsed() == null
                    || serviceHistory.getPartsUsed().isEmpty()) {

                serviceHistory.setPartsUsed(
                        existingHistory.getPartsUsed()
                );
            }

        } else {

            /*
             * NEW SERVICE HISTORY
             */

            // Create service date if one was not supplied
            if (serviceHistory.getServiceDate() == null) {

                serviceHistory.setServiceDate(
                        LocalDateTime.now()
                );
            }

            // Create empty parts array
            if (serviceHistory.getPartsUsed() == null) {

                serviceHistory.setPartsUsed(
                        new ArrayList<>()
                );
            }
        }

        return serviceHistoryRepository.save(
                serviceHistory
        );
    }

    // Delete using MongoDB internal ID
    public void deleteServiceHistory(String id) {

        if (id != null && !id.isBlank()) {
            serviceHistoryRepository.deleteById(id);
        }
    }

    // Delete using custom History ID
    // Example: SH_TEST_01
    public void deleteServiceHistoryByHistoryId(
            String historyId) {

        ServiceHistory serviceHistory =
                serviceHistoryRepository
                        .findByHistoryId(historyId)
                        .orElse(null);

        if (serviceHistory != null) {

            serviceHistoryRepository.delete(
                    serviceHistory
            );
        }
    }

    // Find service history for one vehicle
    public List<ServiceHistory> getHistoryByVehicle(
            Long vehicleId) {

        return serviceHistoryRepository
                .findByVehicleId(vehicleId);
    }

    // Find service history for one booking
    public List<ServiceHistory> getHistoryByBooking(
            Long bookingId) {

        return serviceHistoryRepository
                .findByBookingId(bookingId);
    }

    // Find service history by service type
    public List<ServiceHistory> getHistoryByServiceType(
            String serviceType) {

        return serviceHistoryRepository
                .findByServiceType(serviceType);
    }
}