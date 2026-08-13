package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.document.DiagnosticSummary;
import com.autocare.autocare.mongo_repository.DiagnosticSummaryRepository;

@Service
public class DiagnosticSummaryService {

    private final DiagnosticSummaryRepository diagnosticSummaryRepository;

    public DiagnosticSummaryService(
            DiagnosticSummaryRepository diagnosticSummaryRepository) {

        this.diagnosticSummaryRepository = diagnosticSummaryRepository;
    }

    // Get all diagnostic summaries
    public List<DiagnosticSummary> getAllDiagnosticSummaries() {

        return diagnosticSummaryRepository.findAll();
    }

    // Get one record using MongoDB internal ID
    public DiagnosticSummary getDiagnosticSummaryById(String id) {

        return diagnosticSummaryRepository
                .findById(id)
                .orElse(null);
    }

    // Get one record using custom Diagnostic ID
    // Example: DG001
    public DiagnosticSummary getDiagnosticSummaryByDiagnosticId(
            String diagnosticId) {

        return diagnosticSummaryRepository
                .findByDiagnosticId(diagnosticId)
                .orElse(null);
    }

    // Save new or edited diagnostic summary
    public DiagnosticSummary saveDiagnosticSummary(
            DiagnosticSummary diagnosticSummary) {

        DiagnosticSummary existingDiagnostic =
                diagnosticSummaryRepository
                        .findByDiagnosticId(
                                diagnosticSummary.getDiagnosticId()
                        )
                        .orElse(null);

        /*
         * EDIT EXISTING DIAGNOSTIC SUMMARY
         */
        if (existingDiagnostic != null) {

            // Keep MongoDB internal _id
            diagnosticSummary.setId(
                    existingDiagnostic.getId()
            );

            // Preserve original diagnostic date
            diagnosticSummary.setDiagnosticDate(
                    existingDiagnostic.getDiagnosticDate()
            );

            /*
             * Preserve error codes if the form
             * does not send any error codes.
             */
            if (diagnosticSummary.getErrorCodes() == null
                    || diagnosticSummary.getErrorCodes().isEmpty()) {

                diagnosticSummary.setErrorCodes(
                        existingDiagnostic.getErrorCodes()
                );
            }

        } else {

            /*
             * NEW DIAGNOSTIC SUMMARY
             */

            // Let MongoDB generate internal ObjectId
            diagnosticSummary.setId(null);

            // Automatically create diagnostic date
            if (diagnosticSummary.getDiagnosticDate() == null) {

                diagnosticSummary.setDiagnosticDate(
                        LocalDateTime.now()
                );
            }

            // Create empty error-code array if needed
            if (diagnosticSummary.getErrorCodes() == null) {

                diagnosticSummary.setErrorCodes(
                        new ArrayList<>()
                );
            }
        }

        return diagnosticSummaryRepository.save(
                diagnosticSummary
        );
    }

    // Delete using MongoDB internal ID
    public void deleteDiagnosticSummary(String id) {

        if (id != null && !id.isBlank()) {

            diagnosticSummaryRepository.deleteById(id);
        }
    }

    // Delete using custom Diagnostic ID
    // Example: DG_TEST_01
    public void deleteDiagnosticSummaryByDiagnosticId(
            String diagnosticId) {

        DiagnosticSummary diagnosticSummary =
                diagnosticSummaryRepository
                        .findByDiagnosticId(diagnosticId)
                        .orElse(null);

        if (diagnosticSummary != null) {

            diagnosticSummaryRepository.delete(
                    diagnosticSummary
            );
        }
    }

    // Find diagnostic summaries for one vehicle
    public List<DiagnosticSummary> getDiagnosticsByVehicle(
            Long vehicleId) {

        return diagnosticSummaryRepository
                .findByVehicleId(vehicleId);
    }

    // Find diagnostic summaries for one Job Card
    public List<DiagnosticSummary> getDiagnosticsByJobCard(
            String jobCardId) {

        return diagnosticSummaryRepository
                .findByJobCardId(jobCardId);
    }
}