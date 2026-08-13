package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.document.ComplaintFeedback;
import com.autocare.autocare.mongo_repository.ComplaintFeedbackRepository;

@Service
public class ComplaintFeedbackService {

    private final ComplaintFeedbackRepository complaintFeedbackRepository;

    public ComplaintFeedbackService(
            ComplaintFeedbackRepository complaintFeedbackRepository) {

        this.complaintFeedbackRepository = complaintFeedbackRepository;
    }

    // Get all complaints and feedback
    public List<ComplaintFeedback> getAllComplaintFeedback() {
        return complaintFeedbackRepository.findAll();
    }

    // Get by MongoDB internal ID
    public ComplaintFeedback getComplaintFeedbackById(String id) {
        return complaintFeedbackRepository.findById(id)
                .orElse(null);
    }

    // Get by custom complaint ID
    public ComplaintFeedback getComplaintFeedbackByComplaintId(
            String complaintId) {

        return complaintFeedbackRepository
                .findByComplaintId(complaintId)
                .orElse(null);
    }

    // Save new or edited record
    public ComplaintFeedback saveComplaintFeedback(
            ComplaintFeedback complaintFeedback) {

        ComplaintFeedback existingRecord =
                complaintFeedbackRepository
                        .findByComplaintId(
                                complaintFeedback.getComplaintId()
                        )
                        .orElse(null);

        // EDIT EXISTING RECORD
        if (existingRecord != null) {

            // Keep MongoDB internal ID
            complaintFeedback.setId(
                    existingRecord.getId()
            );

            // Keep original submitted date
            complaintFeedback.setSubmittedAt(
                    existingRecord.getSubmittedAt()
            );

        } else {

            // NEW RECORD

            /*
             * Make sure MongoDB generates a new ObjectId.
             */
            complaintFeedback.setId(null);

            // Automatically create submitted date
            complaintFeedback.setSubmittedAt(
                    LocalDateTime.now()
            );

            /*
             * MongoDB validator allows:
             * Open
             * Under Review
             * Resolved
             * Closed
             */
            if (complaintFeedback.getStatus() == null
                    || complaintFeedback.getStatus().isBlank()) {

                complaintFeedback.setStatus("Open");
            }
        }

        // Validate status
        String status = complaintFeedback.getStatus();

        if (!status.equals("Open")
                && !status.equals("Under Review")
                && !status.equals("Resolved")
                && !status.equals("Closed")) {

            throw new IllegalArgumentException(
                    "Invalid status. Allowed values are Open, Under Review, Resolved and Closed."
            );
        }

        // Validate rating
        if (complaintFeedback.getRating() != null) {

            if (complaintFeedback.getRating() < 1
                    || complaintFeedback.getRating() > 5) {

                throw new IllegalArgumentException(
                        "Rating must be between 1 and 5."
                );
            }
        }

        return complaintFeedbackRepository.save(
                complaintFeedback
        );
    }

    // Delete using MongoDB internal ID
    public void deleteComplaintFeedback(String id) {

        if (id != null && !id.isBlank()) {
            complaintFeedbackRepository.deleteById(id);
        }
    }

    // Delete using custom complaint ID
    public void deleteComplaintFeedbackByComplaintId(
            String complaintId) {

        ComplaintFeedback complaintFeedback =
                complaintFeedbackRepository
                        .findByComplaintId(complaintId)
                        .orElse(null);

        if (complaintFeedback != null) {

            complaintFeedbackRepository.delete(
                    complaintFeedback
            );
        }
    }

    // Find by customer
    public List<ComplaintFeedback> getByCustomer(
            Long customerId) {

        return complaintFeedbackRepository
                .findByCustomerId(customerId);
    }

    // Find by vehicle
    public List<ComplaintFeedback> getByVehicle(
            Long vehicleId) {

        return complaintFeedbackRepository
                .findByVehicleId(vehicleId);
    }

    // Find by type
    public List<ComplaintFeedback> getByType(
            String type) {

        return complaintFeedbackRepository
                .findByType(type);
    }

    // Find by status
    public List<ComplaintFeedback> getByStatus(
            String status) {

        return complaintFeedbackRepository
                .findByStatus(status);
    }
}