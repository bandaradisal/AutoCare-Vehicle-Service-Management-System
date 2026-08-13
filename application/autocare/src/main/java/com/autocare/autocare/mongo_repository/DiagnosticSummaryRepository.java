package com.autocare.autocare.mongo_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.autocare.autocare.document.DiagnosticSummary;

public interface DiagnosticSummaryRepository
        extends MongoRepository<DiagnosticSummary, String> {

    // Find one diagnostic summary using custom diagnostic ID
    Optional<DiagnosticSummary> findByDiagnosticId(String diagnosticId);

    // Find diagnostic summaries for one vehicle
    List<DiagnosticSummary> findByVehicleId(Long vehicleId);

    // Find diagnostic summaries for one job card
    List<DiagnosticSummary> findByJobCardId(String jobCardId);
}