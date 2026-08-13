package com.autocare.autocare.mongo_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.autocare.autocare.document.ComplaintFeedback;

public interface ComplaintFeedbackRepository
        extends MongoRepository<ComplaintFeedback, String> {

    // Find one record by custom complaint ID
    Optional<ComplaintFeedback> findByComplaintId(String complaintId);

    // Find all records for one customer
    List<ComplaintFeedback> findByCustomerId(Long customerId);

    // Find all records for one vehicle
    List<ComplaintFeedback> findByVehicleId(Long vehicleId);

    // Find by type: Complaint or Feedback
    List<ComplaintFeedback> findByType(String type);

    // Find by status
    List<ComplaintFeedback> findByStatus(String status);
}