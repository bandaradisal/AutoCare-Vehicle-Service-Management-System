package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.document.JobCard;
import com.autocare.autocare.mongo_repository.JobCardRepository;

@Service
public class JobCardService {

    private final JobCardRepository jobCardRepository;

    public JobCardService(JobCardRepository jobCardRepository) {
        this.jobCardRepository = jobCardRepository;
    }

    // Get all job cards
    public List<JobCard> getAllJobCards() {
        return jobCardRepository.findAll();
    }

    // Get by MongoDB internal ID
    public JobCard getJobCardById(String id) {
        return jobCardRepository.findById(id)
                .orElse(null);
    }

    // Get by Job Card ID such as JC001
    public JobCard getJobCardByJobCardId(String jobCardId) {
        return jobCardRepository.findByJobCardId(jobCardId)
                .orElse(null);
    }

    // Save new or edited job card
    public JobCard saveJobCard(JobCard jobCard) {

        /*
         * First check whether a job card with this
         * Job Card ID already exists in MongoDB.
         */
        JobCard existingJobCard =
                jobCardRepository
                        .findByJobCardId(jobCard.getJobCardId())
                        .orElse(null);

        /*
         * EDIT EXISTING JOB CARD
         */
        if (existingJobCard != null) {

            /*
             * Keep MongoDB internal _id.
             * This ensures MongoDB performs UPDATE
             * instead of creating a new document.
             */
            jobCard.setId(
                    existingJobCard.getId()
            );

            /*
             * Preserve original opened date.
             */
            jobCard.setOpenedDate(
                    existingJobCard.getOpenedDate()
            );

            /*
             * Preserve existing tasks because
             * the current form does not edit tasks.
             */
            if (existingJobCard.getTasks() != null) {

                jobCard.setTasks(
                        existingJobCard.getTasks()
                );

            } else {

                jobCard.setTasks(
                        new ArrayList<>()
                );
            }

        } else {

            /*
             * NEW JOB CARD
             */

            jobCard.setOpenedDate(
                    LocalDateTime.now()
            );

            if (jobCard.getStatus() == null
                    || jobCard.getStatus().isBlank()) {

                jobCard.setStatus("Open");
            }

            if (jobCard.getTasks() == null) {

                jobCard.setTasks(
                        new ArrayList<>()
                );
            }
        }

        return jobCardRepository.save(jobCard);
    }

    // Delete using MongoDB internal ID
    public void deleteJobCard(String id) {

        if (id != null && !id.isBlank()) {
            jobCardRepository.deleteById(id);
        }
    }

    // Delete using Job Card ID
    public void deleteJobCardByJobCardId(String jobCardId) {

        JobCard jobCard =
                jobCardRepository
                        .findByJobCardId(jobCardId)
                        .orElse(null);

        if (jobCard != null) {
            jobCardRepository.delete(jobCard);
        }
    }

    // Find by vehicle
    public List<JobCard> getJobCardsByVehicle(Long vehicleId) {
        return jobCardRepository.findByVehicleId(vehicleId);
    }

    // Find by technician
    public List<JobCard> getJobCardsByTechnician(Long technicianId) {
        return jobCardRepository.findByTechnicianId(technicianId);
    }

    // Find by status
    public List<JobCard> getJobCardsByStatus(String status) {
        return jobCardRepository.findByStatus(status);
    }
}