package com.autocare.autocare.mongo_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.autocare.autocare.document.JobCard;

public interface JobCardRepository
        extends MongoRepository<JobCard, String> {

    Optional<JobCard> findByJobCardId(String jobCardId);

    List<JobCard> findByVehicleId(Long vehicleId);

    List<JobCard> findByTechnicianId(Long technicianId);

    List<JobCard> findByStatus(String status);
}